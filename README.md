#Pivotal CF - Adding and Using Brokered Services

---
This lab showcases Cloud foundry service broker concepts.

It describes the use of a sample "Hello World" (Java Spring) service broker and the service backend it fronts.

The lab materials consist of this 'readme' document that firstly outlines lab goals, service and broker architecture and implementation aspects, followed by a series on hands on instructions to build, deploy, and use the hello world service broker and the hello world service instances it helps provision.

This is accompanied by the (gradle) projects that provide their sample implementations:

1. A simple Hello World service - which serves as the 'service backend' for:
2. A simple Hello World service broker - which will need to be registered with your Cloud Foundry deployment's Cloud Controller

(Both project are based on a trimmed down copy of the orginal Spring service broker 'template' as provided [here](https://github.com/cloudfoundry-community/spring-service-broker))

**NOTE: service broker registration requires admin privileges on the PCF deployment used**

**If admin access is available, request an admin user to carry out service broker registration**

---
##Lab Goals
* Get a better understanding of the semantics of the service broker V2 API
* Review the architecture / design of a Hello World service broker (and the Hello World service it “fronts”)
* Practical / Hands on:
  * Build and push service backend and service broker WARs
  * Register the broker with the Cloud Controller and allow access
  * Create and access a new service Instance
  * Bind the new instance to an application and test

---
##Hello World Service Anatomy - Architecture
A simple 'hello world' service that showcases the bare minimum functionalities a service would posess: means to manage accounts, means to manage authorizations for accounts, and means to manage the core business logic / functionality the service is to offer.

![inline](images/hw-service-anatomy.png)

---
##Hello-World Service Anatomy - Implementation
Some implementation details:

* Spring MVC based
* Repo (to hold on to accounts / HW instances / authorizations) is in memory (i.e., no persistence)
* ‘Hello World’ service only accessible to authorized users:
  * Have to authenticate
  * Have to be authorized to access the ‘hello world’ “instance”
* A ‘hello world’ instance is represented by the “hello world server” serving (upon a GET) of a string stating: “Hello World [\<instance id>]”

---
##Hello-World Service Broker Anatomy - Architecture
A simple 'hello world' service broker that showcases the bare minimum functionalities a service broker would posess: implementation of the V2 service broker API, and means to interact with the actual service functionality it is to broker access to.

![inline](images/hw-service-broker-anatomy.png)

---
##Hello-World Service Broker Anatomy - Implementation
Some implementation details:

* Spring MVC based
* ‘Mediator’ is the construct that communicates with the Hello World service (using RestTemplate)
* Has to be told per **env vars** where that actual Hello World service resides (+ creds to access)
* A simple authenticator shields access to broker REST calls (password string has to be the reverse of the username provided to successfully authenticate) 

---
##Hello-World Ecosystem
The complete ecosystem consists of a service broker deployment, service deployment, one or more applications that are bound to service instances. PCF's Cloud Controller will interact with the service broker to control service instance lifecycle management, and application to service instance binding.
Note that there are opportunities to test the behaviors of all components either in isolation or in an integrated manner.

---
![inline](images/hw-ecosystem.png)

---
##Hands On: Building and Deploying the HW Service
Build and deploy the HW service (project: hello-world-spring-service) as a PCF application:

```
$ gradle war
… cf api / login / target org & space 
$ cf push
```

contents of manifest.yml:
````
--
applications:
- name: hello-world-spring-service
  memory: 512M
  instances: 1
  host: hello-world-spring-service-${random-word}
  path: ./build/libs/hello-world-spring-service-0.1.war
````

---
##Hands On: Testing the HW service using curl
Excercising the creation of a service instance, an account, and the autorization to allow that account to access the created service instance:

On a local Tomcat instance:

````
$ curl http://admin:admin@localhost:8080/hwinstances/hw1 -X PUT
$ curl  http://admin:admin@localhost:8080/accounts/bob?password=secret -X PUT
$ http://admin:admin@localhost:8080/authorizations/auth1?accountId=bob\&hwInstanceId=hw1 -X PUT
 $ curl http://eddie:secret@localhost:8080/helloworld/hw1
````
After push to a (local) bosh-lite/CF or PCF install:

**NOTE: replace the service URI with the proper value for your deployment!!!**

````
$ curl http://admin:admin@hello-world-spring-service.10.244.0.34.xip.io/hwinstances/hw1 -X PUT
[{"id":"hw1"}]
$ curl http://admin:admin@hello-world-spring-service.10.244.0.34.xip.io/accounts/eddie?password=secret -X PUT
$ curl http://admin:admin@hello-world-spring-service.10.244.0.34.xip.io/accounts
[{"id":"eddie","pwdHash":"Xj8iPz8/Pz8/P3Y/Pz8/aQ=="}]
$ curl http://admin:admin@hello-world-spring-service.10.244.0.34.xip.io/authorizations/authz1?accountId=eddie\&hwInstanceId=hw1 -X PUT
{"id":"authz1","accountId":"eddie","hwInstanceId":"hw1”}
$ curl http://eddie:secret@hello-world-spring-service.10.244.0.34.xip.io/helloworld/hw1
Hello World [hw1]
````

---
##Hands On: Building and Deploying the HW Service Broker

Build and deploy the HW service broker (project: hello-world-spring-service-broker) as a PCF application:

````
$ gradle war
… cf api / login / target org & space 
$ cf push
````

---
contents of manifest.yml:

**NOTE: replace the value for 'HW_SERVICE_HOST' with your Hello World service URI!!!**

````
env:
  HW_SERVICE_HOST: hello-world-spring-service.10.244.0.34.xip.io
  HW_SERVICE_PORT: 80
  HW_SERVICE_ADMIN_USERNAME: admin
  HW_SERVICE_ADMIN_PASSWORD: admin
applications:
- name: hello-world-spring-service-broker
  memory: 512M
  instances: 1
  host: hello-world-spring-service-broker-${random-word}
  path: ./build/libs/hello-world-spring-service-broker-0.1.war
````

---
##Hands On: Testing the HW service broker using curl
**NOTE: replace the sample service broker URI with the proper value for your deployment!!!**


````
$ curl http://admin:admin@hello-world-spring-service-broker.10.244.0.34.xip.io/v2/service_instances/foo -d '{
  "service_id":        "helloworld",
  "plan_id":           "hello-world",
  "organization_guid": "org-guid-here",
  "space_guid":        "space-guid-here"
}' -X PUT -H "Content-Type:application/json"
> > > > > {"dashboard_url":"dash_url"}
$ curl http://admin:admin@hello-world-spring-service-broker.10.244.0.34.xip.io/v2/service_instances/foo/service_bindings/foobinding -d '{
  "service_id": "helloworld",
  "plan_id": "hello-world",
  "app_guid": "my-application-guid"
}' -X PUT -H "Content-Type:application/json"
> > > > {"credentials":{"port":"80","username":"gnidniboof","host":"hello-world-spring-service.10.244.0.34.xip.io","uri":"http://gnidniboof:43ae9@hello-world-spring-service.10.244.0.34.xip.io:80/helloworld/foo","password":"43ae9"},"syslog_drain_url":null}
$  curl http://gnidniboof:43ae9@hello-world-spring-service.10.244.0.34.xip.io:80/helloworld/foo
$ Hello World [foo]
````

---
##Hand On: Create, register, and make the service broker accessible

Create the service broker:

````
$ cf create-service-broker hello-world-service-broker admin admin http://hello-world-spring-service-broker.10.244.0.34.xip.io
Creating service broker hello-world-service-broker as admin...
OK
$ cf service-brokers
Getting service brokers as admin...
name                         url
hello-world-service-broker   http://hello-world-spring-service-broker.10.244.0.34.xip.io
````
---
###Make service broker publically accessible
Find the GUID for the hello world service plan we want to make publically accessible:

**NOTE: the GUID can be found in the 'metadata / guid' entry**

````
$ cf curl /v2/service_plans -X 'GET'
{
  "total_results": 1,
  "total_pages": 1,
  "prev_url": null,
  "next_url": null,
  "resources": [
    {
      "metadata": {
        "guid": "e66d3945-16f8-4060-9e78-13d914634254",
        "url": "/v2/service_plans/e66d3945-16f8-4060-9e78-13d914634254",
        "created_at": "2014-05-26T18:44:52+00:00",
        "updated_at": null
      },
      "entity": {
        "name": "hello-world",
        "free": true,
        "description": "This is a the (only) helloworld plan.",
        "service_guid": "05f9ede0-9eb9-41be-9dfd-edb2f979a4b1",
        "extra": "{}",
        "unique_id": "hello-world",
        "public": false,
        "service_url": "/v2/services/05f9ede0-9eb9-41be-9dfd-edb2f979a4b1",
        "service_instances_url": "/v2/service_plans/e66d3945-16f8-4060-9e78-13d914634254/service_instances"
      }
    }
  ]
}
````

---
###Make the service plan publically accessible

**NOTE: replace the GUID value with the one found using the previous 'curl' invocation**

````
$ cf curl /v2/service_plans/e66d3945-16f8-4060-9e78-13d914634254 -X 'PUT' -d '{"public":true}'
{
  "metadata": {
    "guid": "e66d3945-16f8-4060-9e78-13d914634254",
    "url": "/v2/service_plans/e66d3945-16f8-4060-9e78-13d914634254",
    "created_at": "2014-05-26T18:44:52+00:00",
    "updated_at": "2014-05-26T18:50:20+00:00"
  },
  "entity": {
    "name": "hello-world",
    "free": true,
    "description": "This is a the (only) helloworld plan.",
    "service_guid": "05f9ede0-9eb9-41be-9dfd-edb2f979a4b1",
    "extra": "{}",
    "unique_id": "hello-world",
    "public": true,
    "service_url": "/v2/services/05f9ede0-9eb9-41be-9dfd-edb2f979a4b1",
    "service_instances_url": "/v2/service_plans/e66d3945-16f8-4060-9e78-13d914634254/service_instances"
  }
}
````
The service plan should now show up in the marketplace:

````
$ cf marketplace
Getting services from marketplace in org eph-org / space test as admin...
OK
service             	plans         	description
helloworldService   	hello-world   	helloworld Service Instances
````

---
##Hands On: Create, bind, and use a service instance
Create a hello world service instance:


````
$ cf create-service helloworldService hello-world hw1
Creating service hw1 in org eph-org / space test as admin...
OK
$ cf services
Getting services in org eph-org / space test as admin...
OK
Name     service                    plan              bound apps
hw1        helloworldService   hello-world
````

---
Bind the instance to a sample app (e.g., spring-hello-env):

````
$ cf push she -p target/spring-hello-env.war –no-start
…
$ cf bind-service she hw1
…
$ cf push she -p target/spring-hello-env.war
…
````

---
Browse to spring-hello-env and show 'environment':

![inline](images/spring-hw-env.png)

---
Browse to the provided 'hello world' URL (the values of the 'uri' element in the 'helloworldService' element in VCAP_SERVICES):

![inline](images/spring-hw-url.png)

---
##TODO / Further Work Suggestions
* Clean-up
  * E.g., use a __user defined service__ instance to provide Hello world service connection details to the Hello world service broker
* Convert to __Spring Boot__
* See how the Hello World broker differs from the approach taken in https://github.com/cloudfoundry-community/spring-service-broker and https://github.com/cloudfoundry-community/spring-boot-cf-service-broker(note the need for ‘state maintenance’ in the broker…)

---
## Resources
* [http://docs.gopivotal.com/pivotalcf/services/api.html](http://docs.gopivotal.com/pivotalcf/services/api.html)
* [http://docs.gopivotal.com/pivotalcf/services/managing-service-brokers.html](http://docs.gopivotal.com/pivotalcf/services/managing-service-brokers.html)
* [http://docs.gopivotal.com/pivotalcf/services/binding-credentials.html](http://docs.gopivotal.com/pivotalcf/services/binding-credentials.html)
* [Tiny sample application: https://github.com/cloudfoundry-samples/spring-hello-env](https://github.com/cloudfoundry-samples/spring-hello-env)



package org.cloudfoundry.community.helloworldservice.service;

import org.cloudfoundry.community.helloworldservice.model.HWInstance;

import java.util.List;

public interface HWInstanceService {

    public List<HWInstance> getAllHWInstances();
    public HWInstance getHWInstance(String hwInStanceId);

    public void createHWInstance(HWInstance hwInstance);
    public void destroyHWInstance(String hwInstanceId);

    public boolean hwInstanceExists(String hwInstanceId);

}

package org.cloudfoundry.community.helloworldservice.service;

import org.cloudfoundry.community.helloworldservice.model.HWInstance;
import org.cloudfoundry.community.helloworldservice.repo.Repo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HWInstanceServiceImpl implements HWInstanceService {

    @Autowired
    Repo repo;

    @Override
    public List<HWInstance> getAllHWInstances() {
        return repo.getHWInstances();
    }

    @Override
    public HWInstance getHWInstance(String hwInStanceId) {
        return repo.getHWInstance(hwInStanceId);
    }

    @Override
    public void createHWInstance(HWInstance hwInstance) {
        repo.addHWInstance(hwInstance);
    }

    @Override
    public void destroyHWInstance(String hwInstanceId) {
        repo.deleteHWInstance(hwInstanceId);
    }

    @Override
    public boolean hwInstanceExists(String hwInstanceId) {
        return repo.hwInstanceExists(hwInstanceId);
    }
}

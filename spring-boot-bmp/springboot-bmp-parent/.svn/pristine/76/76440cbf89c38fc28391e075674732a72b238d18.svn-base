package com.newcore.bmp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.newcore.bmp.models.authority.models.Resource;
import com.newcore.bmp.service.api.authority.ResourceService;
import com.newcore.bmp.dao.api.authority.ResourceDao;

import java.util.List;

@Service("resourceService")
public class ResourceServiceImpl implements ResourceService {

    @Autowired
    private ResourceDao resourceDao;

    @Override
    public Resource createResource(Resource resource) {
        return resourceDao.createResource(resource);
    }

    @Override
    public Resource updateResource(Resource resource) {
        return resourceDao.updateResource(resource);
    }

    @Override
    public void deleteResource(String resourceId) {
        resourceDao.deleteResource(resourceId);
    }

    @Override
    public Resource findOne(String resourceId) {
        return resourceDao.findOne(resourceId);
    }

    @Override
    public List<Resource> findAll() {
        return resourceDao.findAll();
    }

    @Override
    public List<Resource> findResByClerkNo(String clerkNo) {
        return resourceDao.findResByClerkNo(clerkNo);
    }

    @Override
    public List<Resource> comMenuSearch(String clerkNo) {
        return resourceDao.comMenu(clerkNo);
    }

    @Override
    public List<Resource> toolsSearch(String clerkNo) {
        return resourceDao.toolsSearch(clerkNo);
    }

    @Override
    public List<Resource> toolsCombo(String clerkNo) {
        
        return resourceDao.toolsCombo(clerkNo);
    }

    @Override
    public List<Resource> sidebarMenuSearch(String clerkNo, String resourceName) {
        
        return resourceDao.sidebarMenuSearch(clerkNo, resourceName);
    }
}

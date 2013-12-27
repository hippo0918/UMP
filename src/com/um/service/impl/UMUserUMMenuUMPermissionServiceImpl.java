package com.um.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.um.service.IUMUserUMMenuUMPermissionService;

@Service(IUMUserUMMenuUMPermissionService.SERVICE_NAME)
@Transactional(readOnly=true)
public class UMUserUMMenuUMPermissionServiceImpl implements IUMUserUMMenuUMPermissionService {

}

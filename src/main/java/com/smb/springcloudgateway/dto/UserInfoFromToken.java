package com.smb.springcloudgateway.dto;

import com.smb.coremodel.enums.WorkplaceType;
import lombok.Data;

@Data
public class UserInfoFromToken {
    private Integer userRoleId;
    private String solId;
    private String userName;
    private String userGroup;
    private String branchName;
    private WorkplaceType workplaceType;
}

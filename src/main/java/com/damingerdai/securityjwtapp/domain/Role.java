package com.damingerdai.securityjwtapp.domain;

/**
 * @author gming001
 * @version 2022-06-25 22:33
 */
public class Role {

    private String id;

    private String roleName;

    private String roleDescription;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleDescription() {
        return roleDescription;
    }

    public void setRoleDescription(String roleDescription) {
        this.roleDescription = roleDescription;
    }

    public Role() {
        super();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Role{");
        sb.append("id='").append(id).append('\'');
        sb.append(", roleName='").append(roleName).append('\'');
        sb.append(", roleDescription='").append(roleDescription).append('\'');
        sb.append('}');
        return sb.toString();
    }
}

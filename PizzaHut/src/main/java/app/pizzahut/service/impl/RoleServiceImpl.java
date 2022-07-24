package app.pizzahut.service.impl;

import app.pizzahut.exception.RoleNotFoundException;
import app.pizzahut.model.RoleModel;
import app.pizzahut.model.UserModel;
import app.pizzahut.repo.RoleRepo;
import app.pizzahut.repo.UserRepo;
import app.pizzahut.service.RoleService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RoleServiceImpl implements RoleService {

    @Autowired
    RoleRepo roleRepo;

    @Autowired
    UserRepo userRepo;

    @Override
    public String getRoleNameFromUser(UserModel userModel) {
        RoleModel roleModel = roleRepo.findById(userModel.getRole_id())
                .orElseThrow(() -> new RoleNotFoundException("Bullshit!"));
        return roleModel.getName();
    }

}

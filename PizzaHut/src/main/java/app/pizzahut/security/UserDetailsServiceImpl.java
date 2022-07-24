package app.pizzahut.security;

import app.pizzahut.model.RankModel;
import app.pizzahut.exception.RankNotFoundException;
import app.pizzahut.repo.RankRepo;
import app.pizzahut.response.UserModelRes;
import app.pizzahut.model.RoleModel;
import app.pizzahut.exception.RoleNotFoundException;
import app.pizzahut.repo.RoleRepo;
import app.pizzahut.model.UserModel;
import app.pizzahut.repo.UserRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserRepo userRepo;

    @Autowired
    RankRepo rankRepo;

    @Autowired
    RoleRepo roleRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserModel userModel = userRepo.findByMobile(username);

        if (userModel == null) throw new UsernameNotFoundException(username);

        Long rankId = userModel.getRank_id();
        Long roleId = userModel.getRole_id();
        Long userId = userModel.getId();

        RankModel rankModel = rankRepo.findById(rankId)
                .orElseThrow(() -> new RankNotFoundException("Không tìm thấy rank id: " + rankId + " tại user id: " + userId));

        RoleModel roleModel = roleRepo.findById(roleId)
                .orElseThrow(() -> new RoleNotFoundException("Không tìm thấy role id: " + roleId + " tại user id: " + userId));

        UserModelRes res = new UserModelRes();
        res.setId(userModel.getId());
        res.setHoTen(userModel.getHoTen());
        res.setMobile(userModel.getMobile());
        res.setAddress(userModel.getAddress());
        res.setPoint(userModel.getPoint());
        res.setRankName(rankModel.getName());
        res.setRoleName(roleModel.getName());
        res.setPassword(userModel.getPassword());

        log.info("---------------------" + res);
        return new MyUserDetails(res);
    }

}

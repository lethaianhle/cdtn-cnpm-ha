package app.pizzahut.service.impl;

import app.pizzahut.exception.DuplicateMobileNumberException;
import app.pizzahut.exception.UserNotFoundException;
import app.pizzahut.model.RankModel;
import app.pizzahut.model.UserModel;
import app.pizzahut.exception.RankNotFoundException;
import app.pizzahut.repo.RankRepo;
import app.pizzahut.repo.UserRepo;
import app.pizzahut.response.UserModelRes;
import app.pizzahut.model.RoleModel;
import app.pizzahut.exception.RoleNotFoundException;
import app.pizzahut.repo.RoleRepo;
import app.pizzahut.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepo userRepo;

    @Autowired
    RankRepo rankRepo;

    @Autowired
    RoleRepo roleRepo;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public UserModel get(Long id) {
        return userRepo.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Không tìm thấy user id: " + id));
    }

    @Override
    public List<UserModel> findAll() {
        return userRepo.findAll();
    }

    @Override
    public List<UserModelRes> findAllRes() {
        List<UserModel> userModelList = userRepo.findAll();
        List<UserModelRes> result = new ArrayList<>();

        for (int i = 0; i < userModelList.size(); i++) {
            Long rankId = userModelList.get(i).getRank_id();
            Long roleId = userModelList.get(i).getRole_id();
            Long userId = userModelList.get(i).getId();

            RankModel rankModel = rankRepo.findById(rankId)
                    .orElseThrow(() -> new RankNotFoundException("Không tìm thấy rank id: " + rankId + " tại user id: " + userId));

            RoleModel roleModel = roleRepo.findById(roleId)
                    .orElseThrow(() -> new RoleNotFoundException("Không tìm thấy role id: " + roleId + " tại user id: " + userId));

            UserModelRes res = new UserModelRes();
            res.setId(userModelList.get(i).getId());
            res.setHoTen(userModelList.get(i).getHoTen());
            res.setMobile(userModelList.get(i).getMobile());
            res.setAddress(userModelList.get(i).getAddress());
            if (userModelList.get(i).getShippingAddress1() != null) {
                res.setShippingAddress1(userModelList.get(i).getShippingAddress1());
            }
            if (userModelList.get(i).getShippingAddress2() != null) {
                res.setShippingAddress2(userModelList.get(i).getShippingAddress2());
            }
            if (userModelList.get(i).getShippingAddress3() != null) {
                res.setShippingAddress3(userModelList.get(i).getShippingAddress3());
            }
            res.setPoint(userModelList.get(i).getPoint());
            res.setRankName(rankModel.getName());
            res.setRoleName(roleModel.getName());

            result.add(res);
        }

        return result.size() > 0 ? result : null;
    }

    @Override
    public UserModelRes getRes(Long id) {
        UserModel userModel = userRepo.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Không tìm thấy user id: " + id));

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
        if (userModel.getShippingAddress1() != null) {
            res.setShippingAddress1(userModel.getShippingAddress1());
        }
        if (userModel.getShippingAddress2() != null) {
            res.setShippingAddress2(userModel.getShippingAddress2());
        }
        if (userModel.getShippingAddress3() != null) {
            res.setShippingAddress3(userModel.getShippingAddress3());
        }
        res.setPoint(userModel.getPoint());
        res.setRankName(rankModel.getName());
        res.setRoleName(roleModel.getName());

        return res;
    }

    @Override
    public void delete(Long id) {
        UserModel userModel = userRepo.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Không tìm thấy user với id: " + id));

        userRepo.delete(userModel);
    }

    @Override
    public UserModelRes save(UserModelRes userModelRes) {
        if (userModelRes.getId() == null) {

            if (isDuplicateMobile(userModelRes.getId(), userModelRes.getMobile()))
                throw new DuplicateMobileNumberException("Tạo mới thành viên thất bại! Số điện thoại đã được sử dụng");


            RoleModel roleModel = roleRepo.findRoleModelByName(userModelRes.getRoleName())
                    .orElseThrow(() -> new RoleNotFoundException("Không tìm thấy role name: " + userModelRes.getRoleName() + " tại userRes"));

            UserModel userModel = new UserModel();
            userModel.setHoTen(userModelRes.getHoTen());
            userModel.setMobile(userModelRes.getMobile()); // check duplicate mobile
            userModel.setRole_id(roleModel.getId());
            userModel.setPassword("$2a$12$jO20hADgIB2qo5DNyUJ83OrtNPc98r5HLk9CMSB1EvpLiUiQJv5Am");
            userModel.setPoint(userModelRes.getPoint());

            if (userModelRes.getPoint() < 100) {
                userModel.setRank_id(3L);
            } else if (userModelRes.getPoint() >= 100 && userModelRes.getPoint() < 200) {
                userModel.setRank_id(2L);
            } else {
                userModel.setRank_id(1L);
            }

            userModel.setAddress(userModelRes.getAddress());
            if (userModelRes.getShippingAddress1() != null) {
                userModel.setShippingAddress1(userModelRes.getShippingAddress1());
            }
            if (userModelRes.getShippingAddress2() != null) {
                userModel.setShippingAddress2(userModelRes.getShippingAddress2());
            }
            if (userModelRes.getShippingAddress3() != null) {
                userModel.setShippingAddress3(userModelRes.getShippingAddress3());
            }

            UserModel userCreated = userRepo.save(userModel);

            return mapper(userCreated);

        } else {
            if (isDuplicateMobile(userModelRes.getId(), userModelRes.getMobile())) {
                throw new DuplicateMobileNumberException("Cập nhập thông tin thất bại! Số điện thoại đã được sử dụng");
            }

            UserModel userNeedToUpdate = userRepo.findById(userModelRes.getId())
                    .orElseThrow(() -> new UserNotFoundException("Không tìm thấy user id: " + userModelRes.getId() + " tại userRes"));

            RoleModel roleModel = roleRepo.findRoleModelByName(userModelRes.getRoleName())
                    .orElseThrow(() -> new RoleNotFoundException("Không tìm thấy role name: " + userModelRes.getRoleName() + " tại userRes"));

            RankModel rankModel = rankRepo.findRankModelByName(userModelRes.getRankName())
                    .orElseThrow(() -> new RankNotFoundException("Không tìm thấy rank name: " + userModelRes.getRankName() + " tại userRes"));

            UserModel userModel = new UserModel();
            userModel.setId(userModelRes.getId());
            userModel.setHoTen(userModelRes.getHoTen());
            userModel.setMobile(userModelRes.getMobile()); // check duplicate mobile
            userModel.setRole_id(roleModel.getId());
            userModel.setPoint(userModelRes.getPoint());
            if (userModelRes.getShippingAddress1() != null) {
                userModel.setShippingAddress1(userModelRes.getShippingAddress1());
            }
            if (userModelRes.getShippingAddress2() != null) {
                userModel.setShippingAddress2(userModelRes.getShippingAddress2());
            }
            if (userModelRes.getShippingAddress3() != null) {
                userModel.setShippingAddress3(userModelRes.getShippingAddress3());
            }

            if (userModelRes.getPoint() < 100) {
                userModel.setRank_id(3L);
            } else if (userModelRes.getPoint() >= 100 && userModelRes.getPoint() < 200) {
                userModel.setRank_id(2L);
            } else {
                userModel.setRank_id(1L);
            }

            userModel.setAddress(userModelRes.getAddress());
            userModel.setPassword(userNeedToUpdate.getPassword());
            UserModel userUpdated = userRepo.save(userModel);

            return mapper(userUpdated);
        }
    }

    @Override
    public void register(UserModelRes userModelRes) {
        if (isDuplicateMobile(userModelRes.getId(), userModelRes.getMobile())) {
            throw new DuplicateMobileNumberException("Đăng ký tài khoản thất bại! Số điện thoại đã tồn tại");
        }

        UserModel userModel = new UserModel();
        userModel.setHoTen(userModelRes.getHoTen());
        userModel.setMobile(userModelRes.getMobile());
        userModel.setPassword(passwordEncoder.encode(userModelRes.getPassword()));
        userModel.setRole_id(2L);
        userModel.setPoint(0);
        userModel.setAddress(userModelRes.getAddress());

        if (userModelRes.getShippingAddress1() != null) {
            userModel.setShippingAddress1(userModelRes.getShippingAddress1());
        }
        if (userModelRes.getShippingAddress2() != null) {
            userModel.setShippingAddress2(userModelRes.getShippingAddress2());
        }
        if (userModelRes.getShippingAddress3() != null) {
            userModel.setShippingAddress3(userModelRes.getShippingAddress3());
        }

        if (userModelRes.getPoint() < 100) {
            userModel.setRank_id(3L);
        } else if (userModelRes.getPoint() >= 100 && userModelRes.getPoint() < 200) {
            userModel.setRank_id(2L);
        } else {
            userModel.setRank_id(1L);
        }

        userRepo.save(userModel);
    }

    private UserModelRes mapper(UserModel userModel) {
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
        if (userModel.getShippingAddress1() != null) {
            res.setShippingAddress1(userModel.getShippingAddress1());
        }
        if (userModel.getShippingAddress2() != null) {
            res.setShippingAddress2(userModel.getShippingAddress2());
        }
        if (userModel.getShippingAddress3() != null) {
            res.setShippingAddress3(userModel.getShippingAddress3());
        }
        res.setPoint(userModel.getPoint());
        res.setRankName(rankModel.getName());
        res.setRoleName(roleModel.getName());

        return res;
    }

    public boolean isDuplicateMobile(Long id, String mobile) {
        UserModel userByMobile = userRepo.findByMobile(mobile);

        if(userByMobile == null) {
            return false;
        }

        if (id == null) {
            if(userByMobile == null) {
                return false;
            }
        } else {
            if (userByMobile.getId() == id) {
                return false;
            }
        }

        return true;
    }

}

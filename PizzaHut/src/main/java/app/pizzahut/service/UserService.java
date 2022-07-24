package app.pizzahut.service;

import app.pizzahut.model.UserModel;
import app.pizzahut.response.UserModelRes;

import java.util.List;

public interface UserService {

    UserModel get(Long id);

    List<UserModel> findAll();

    List<UserModelRes> findAllRes();

    UserModelRes getRes(Long id);

    void delete(Long id);

    UserModelRes save(UserModelRes userModelRes);

    void register(UserModelRes userModelRes);
}

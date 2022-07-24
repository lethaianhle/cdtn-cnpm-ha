package app.pizzahut.user;

import app.pizzahut.model.OrderModel;
import app.pizzahut.model.RankModel;
import app.pizzahut.model.UserModel;
import app.pizzahut.repo.OrderRepo;
import app.pizzahut.repo.RankRepo;
import app.pizzahut.model.RoleModel;
import app.pizzahut.repo.RoleRepo;
import app.pizzahut.repo.UserRepo;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
class UserRepoTest {

    @Autowired
    UserRepo userRepo;

    @Autowired
    RoleRepo roleRepo;

    @Autowired
    RankRepo rankRepo;

    @Autowired
    OrderRepo orderRepo;

    @Test
    void initData1() {
        RankModel rankModel = new RankModel();
        rankModel.setName("Platinum");
        RankModel rankModel2 = new RankModel();
        rankModel2.setName("Gold");
        RankModel rankModel3 = new RankModel();
        rankModel3.setName("Silver");
        rankRepo.save(rankModel);
        rankRepo.save(rankModel2);
        rankRepo.save(rankModel3);

        RoleModel roleModel = new RoleModel();
        roleModel.setName("ADMIN");
        RoleModel roleModel2 = new RoleModel();
        roleModel2.setName("USER");
        roleRepo.save(roleModel);
        roleRepo.save(roleModel2);
    }

    @Test
    void initData2() {
        UserModel userModel = new UserModel();
        userModel.setHoTen("Nguyễn Hoàng Anh");
        userModel.setMobile("098713883");
        userModel.setPoint(0);
        userModel.setPassword("$2a$12$jO20hADgIB2qo5DNyUJ83OrtNPc98r5HLk9CMSB1EvpLiUiQJv5Am");
        userModel.setAddress("Vĩnh Diện, Đống Đa, Thạch Thất");
        userModel.setRank_id(3L);
        userModel.setRole_id(2L);
        userRepo.save(userModel);

        UserModel userModel2 = new UserModel();
        userModel2.setHoTen("Nguyễn Văn Mạnh");
        userModel2.setMobile("0134131353");
        userModel2.setPoint(120);
        userModel2.setPassword("$2a$12$jO20hADgIB2qo5DNyUJ83OrtNPc98r5HLk9CMSB1EvpLiUiQJv5Am");
        userModel2.setAddress("Thụy Khuê, Thiên Hạ, Hà Nội");
        userModel2.setRank_id(2L);
        userModel2.setRole_id(2L);
        userRepo.save(userModel2);

        UserModel userModel3 = new UserModel();
        userModel3.setHoTen("Hoàng Thị Hà");
        userModel3.setMobile("0979842998");
        userModel3.setPoint(210);
        userModel3.setPassword("$2a$12$jO20hADgIB2qo5DNyUJ83OrtNPc98r5HLk9CMSB1EvpLiUiQJv5Am");
        userModel3.setAddress("Trần Thái Tông, Hai Bà Trưng, Hà Nội");
        userModel3.setRank_id(1L);
        userModel3.setRole_id(2L);
        userRepo.save(userModel3);

        UserModel userModel4 = new UserModel();
        userModel4.setHoTen("Admin");
        userModel4.setMobile("0123456789");
        userModel4.setPoint(0);
        userModel4.setPassword("$2a$12$jO20hADgIB2qo5DNyUJ83OrtNPc98r5HLk9CMSB1EvpLiUiQJv5Am");
        userModel4.setAddress("Nam Từ Liêm, Hà Nội");
        userModel4.setRank_id(1L);
        userModel4.setRole_id(1L);
        userRepo.save(userModel4);
    }

    @Test
    void initData3() {
        OrderModel orderModel = new OrderModel();
        orderModel.setUserId(1L);
        orderModel.setShippingAddress("Vĩnh Diện, Đống Đa, Thạch Thất");
        orderModel.setTotalPrice(1_500_000);
        orderRepo.save(orderModel);

        OrderModel orderModel1 = new OrderModel();
        orderModel1.setUserId(1L);
        orderModel1.setShippingAddress("Vĩnh Diện, Đống Đa, Thạch Thất");
        orderModel1.setTotalPrice(500_000);
        orderRepo.save(orderModel1);

        OrderModel orderModel2 = new OrderModel();
        orderModel2.setUserId(2L);
        orderModel2.setShippingAddress("Thụy Khuê, Thiên Hạ, Hà Nội");
        orderModel2.setTotalPrice(1_565_000);
        orderRepo.save(orderModel2);

        OrderModel orderModel3 = new OrderModel();
        orderModel3.setUserId(4L);
        orderModel3.setShippingAddress("Nam Từ Liêm, Hà Nội");
        orderModel3.setTotalPrice(6_000);
        orderRepo.save(orderModel3);
    }

    @Test
    void findByMobile() {
        UserModel userModel = userRepo.findByMobile("0134131353");
        log.info(userModel.toString());
        assertNotNull(userModel);
    }

    @Test
    void countAllByMobile() {
        Long i = userRepo.countAllByMobile("0123456789");
        log.info(String.valueOf(i));
        assertNotEquals(0, i);
    }

    @Test
    void isDuplicated() {
        System.out.println(isDuplicatedMobile(1L, "09871388344"));
    }

    public boolean isDuplicatedMobile(Long id, String mobile) {
        UserModel userByMobile = userRepo.findByMobile(mobile);

        if(userByMobile == null) {
            return false;
        }

        if (id == null) {
            if(userByMobile == null) {
                return false;
            }
        } else {
            if (userByMobile.getId() != id) {
                return false;
            }
        }

        return true;
    }

}
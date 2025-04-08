package hr.vpetrina.webshop.repository;


import hr.vpetrina.webshop.model.UserPurchase;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

public interface UserPurchaseRepository {
    UserPurchase insert(UserPurchase userPurchase);
    Optional<UserPurchase> getById(Integer id);
    List<UserPurchase> getAll();
    List<UserPurchase> getByUserId(Integer userId);
    List<UserPurchase> getFiltered(Integer userId, Date from, Date to);
}

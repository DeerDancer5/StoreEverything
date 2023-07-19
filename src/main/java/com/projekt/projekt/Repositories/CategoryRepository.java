package com.projekt.projekt.Repositories;
import com.projekt.projekt.Notes.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;


@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {
    Optional<Category> findByName(String name);
    @Query("SELECT c FROM Category c, Note n  WHERE n.category=c AND n.user.username =:userName ")
    List<Category> getUsersCategories(String userName);

}

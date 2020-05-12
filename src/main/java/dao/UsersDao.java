 package dao;


 import models.News;
 import models.Users;

 import java.util.List;

 public interface UsersDao {
  //create
  void add(Users users);

  //read
  List<Users> getAll();
  Users findById(int id);
  List<Users> getAllUsersByDepartments(int departmentId);



  //delete
  void deleteById(int id);
  void clearAll();

}

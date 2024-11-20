package hiber;

import hiber.config.AppConfig;
import hiber.model.Car;
import hiber.model.User;
import hiber.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.SQLException;
import java.util.List;

public class MainApp {
   public static void main(String[] args) throws SQLException {
      AnnotationConfigApplicationContext context =
            new AnnotationConfigApplicationContext(AppConfig.class);

      UserService userService = context.getBean(UserService.class);

      // Создаем машины
      Car car1 = new Car("Toyota", 2021);
      Car car2 = new Car("BMW", 2023);

      // Создаем пользователей и связываем их с машинами
      User user1 = new User("User1", "Lastname1", "user1@mail.ru");
      user1.setCar(car1);

      User user2 = new User("User2", "Lastname2", "user2@mail.ru");
      user2.setCar(car2);

      // Добавляем пользователей с машинами в базу данных
      userService.add(user1);
      userService.add(user2);

      // Извлекаем пользователей и их машины
      List<User> users = userService.listUsers();
      for (User user : users) {
         System.out.println("User ID: " + user.getId());
         System.out.println("First Name: " + user.getFirstName());
         System.out.println("Last Name: " + user.getLastName());
         System.out.println("Email: " + user.getEmail());
         System.out.println("Car Model: " + (user.getCar() != null ? user.getCar().getModel() : "No car"));
         System.out.println("Car Series: " + (user.getCar() != null ? user.getCar().getSeries() : "No car"));
         System.out.println();
      }

      // Поиск пользователя по модели и серии машины
      User userWithCar = userService.findUserByCar("Toyota", 2021);
      if (userWithCar != null) {
         System.out.println("Owner of Toyota 2021: " + userWithCar.getFirstName() + " " + userWithCar.getLastName());
      } else {
         System.out.println("No user found with Toyota 2021");
      }

      context.close();
   }
}

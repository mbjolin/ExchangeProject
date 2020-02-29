package ca.amazing.exchangeproject.fake;

import ca.amazing.exchangeproject.domain.Activity;
import ca.amazing.exchangeproject.domain.User;
import ca.amazing.exchangeproject.repository.ActivityRepository;
import ca.amazing.exchangeproject.repository.UserRepository;
import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

@Component
public class FakeClass {

  private List<Activity> activityList = new LinkedList<>();
  private Integer activityNumber = 6;
  private ActivityRepository activityRepo;

  private List<User> userList = new LinkedList<>();
  private Integer userNumber = 52;
  private UserRepository userRepo;

  private static final Faker faker = new Faker();

  @Autowired
  public FakeClass(
      ActivityRepository activityRepo,
      UserRepository userRepo) {
    this.activityRepo = activityRepo;
    this.userRepo = userRepo;

    createUser();
    createActivity();
  }


  private void createLoopActivity(int start, int end) {
    for (Integer i = start; i < end; i++) {
      Activity activity = new Activity();
      activity.setId(i.longValue());
      activity.setName(faker.book().title());
      activityRepo.save(activity);
      activityList.add(activity);
    }
  }

  /* 6 req/sec pour BD h2 file creation activity. */
  /* 43 req/sec pour BD h2 file creation activity sans FAKER */
  /* sans cascade c'est instant */
  private void createActivity() {
    int activityNumThread = activityNumber / 4;

    for (int i = 0; i < 5; i++) {
      int start = i * activityNumThread;
      int end = (i + 1) * activityNumThread;
      Runnable runnable =
          () -> {
            createLoopActivity(start, end);
          };

      Thread t = new Thread(runnable);
      t.start();
    }
  }


  private void createUser() {

    for (Integer i = 0; i < userNumber; i++) {
      User user = new User();
      user.setId(i.longValue());
      user.setName(faker.name().fullName());
      user.setEmail(faker.name().username() + "@email.com");
      userRepo.save(user);
      userList.add(user);
    }
  }

}

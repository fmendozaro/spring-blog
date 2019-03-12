package us.tacos4.blog.seeders;

import us.tacos4.blog.models.Post;
import us.tacos4.blog.models.Tag;
import us.tacos4.blog.models.User;
import us.tacos4.blog.models.UserRole;
import us.tacos4.blog.repositories.PostRepository;
import us.tacos4.blog.repositories.TagRepository;
import us.tacos4.blog.repositories.UserRoles;
import us.tacos4.blog.repositories.UsersRepository;
import com.github.javafaker.Faker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class DbSeeder implements CommandLineRunner{

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final PostRepository postDao;
    private final UsersRepository userDao;
    private final TagRepository tagDao;
    private final UserRoles userRoles;
    private final Faker faker;
    private final PasswordEncoder passwordEncoder;
    @Value("${app.env}")
    private String environment;

    public DbSeeder(PostRepository postDao, UsersRepository userDao, TagRepository tagDao, PasswordEncoder passwordEncoder, UserRoles userRoles){
        this.postDao = postDao;
        this.userDao = userDao;
        this.tagDao = tagDao;
        this.passwordEncoder = passwordEncoder;
        this.userRoles = userRoles;
        this.faker = new Faker();
    }

    private void seedTags(){

        List<Tag> tags = Arrays.asList(
                new Tag("General"),
                new Tag("Tech"),
                new Tag("Music"),
                new Tag("Art"),
                new Tag("Programming"),
                new Tag("Food"),
                new Tag("Films")
        );

        tagDao.saveAll(tags);
    }

    private void seedUsers(){

        List<User> users = Arrays.asList(
                new User("fer", "fer@mail.com", passwordEncoder.encode("pass"), null),
                new User("luis", "luis@mail.com", passwordEncoder.encode("pass"),null),
                new User("zach", "zach@mail.com", passwordEncoder.encode("pass"), null),
                new User("justin", "justin@mail.com", passwordEncoder.encode("pass"), null),
                new User("ryan", "ryan@mail.com", passwordEncoder.encode("pass"), null)
        );

        userDao.saveAll(users);

        for (User user: users) {
            UserRole ur = new UserRole();
            ur.setRole("ROLE_USER");
            ur.setUserId(user.getId());
            userRoles.save(ur);
        }

    }

    private void seedPosts(){

        long rnd = 0;
        List<Post> posts = new ArrayList<>();
        List<User> users =  userDao.findAll();
        List<Tag> allTags = tagDao.findAll();
        List<Tag> randomTags;

        for (User user: users) {
            System.out.println("user = " + user);
        }

        for(int i = 0;i <= 50;i++){
            randomTags = new ArrayList<>();

            rnd = (long) 1 + (int)(Math.random() * ((users.size() - 1) + 1));

            // Generates a random user
            User randomUser = userDao.getOne(rnd);

            rnd = (long) 1 + (int)(Math.random() * ((allTags.size() - 1) + 1));

            // Adds 2 random tags from the existing list
            randomTags.add(tagDao.getOne(rnd));
            rnd = (long) 1 + (int)(Math.random() * ((allTags.size() - 1) + 1));
            randomTags.add(tagDao.getOne(rnd));

            posts.add(new Post(this.faker.chuckNorris().fact(), this.faker.lorem().paragraph(6), randomTags, randomUser, null));
        }

        for (Post post: posts) {
            System.out.println("post = " + post);
        }

        postDao.saveAll(posts);

    }

    @Override
    public void run(String... strings) throws Exception {

        if(!environment.equals("dev")){
            log.info("app.env is not in dev mode, skip the seeders");
            return;
        }

        log.info("Deleting tags...");
        tagDao.deleteAll();

        log.info("Deleting users...");
        userDao.deleteAll();

        log.info("Deleting posts");
        postDao.deleteAll();

        log.info("Seeding tags...");
        this.seedTags();

        log.info("Seeding users...");
        this.seedUsers();

        log.info("Seeding posts...");
        this.seedPosts();

        log.info("Finished running seeders!");

    }
}

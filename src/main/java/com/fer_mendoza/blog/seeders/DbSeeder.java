package com.fer_mendoza.blog.seeders;

import com.fer_mendoza.blog.models.*;
import com.fer_mendoza.blog.repositories.*;
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
    private final CommentRepository commentRepository;
    @Value("${seed.db}")
    private String environment;

    public DbSeeder(PostRepository postDao, UsersRepository userDao, TagRepository tagDao, PasswordEncoder passwordEncoder, UserRoles userRoles, CommentRepository commentRepository){
        this.postDao = postDao;
        this.userDao = userDao;
        this.tagDao = tagDao;
        this.passwordEncoder = passwordEncoder;
        this.userRoles = userRoles;
        this.faker = new Faker();
        this.commentRepository = commentRepository;
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

            posts.add(new Post(this.faker.chuckNorris().fact(), this.faker.lorem().paragraph(6), randomTags, randomUser, null, null));
        }

        for (Post post: posts) {
            System.out.println("post = " + post);
        }

        List<Post> savedPosts = postDao.saveAll(posts);

        for (Post savedPost: savedPosts) {
            Comment savedComment = commentRepository.save(createRandomComment(users, savedPost, null));
            commentRepository.save(createRandomComment(users, null, savedComment));
            commentRepository.save(createRandomComment(users, null, savedComment));
            commentRepository.save(createRandomComment(users, null, savedComment));
        }

    }

    public Comment createRandomComment(List<User> users, Post post, Comment parentComment){
        long rnd = (long) 1 + (int)(Math.random() * ((users.size() - 1) + 1));
        User randomUser = userDao.getOne(rnd);
        Comment comment = new Comment();
        comment.setBody(this.faker.harryPotter().quote());
        comment.setUser(randomUser);
        if(post != null){
            comment.setPost(post);
        }
        if(parentComment != null){
            comment.setParent(parentComment);
        }
        return comment;
    }

    @Override
    public void run(String... strings) throws Exception {

        if(!environment.equals("dev")){
            log.info("seed.db is not in dev mode, skip the seeders");
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

        log.info("Seeding posts and comments...");
        this.seedPosts();

        log.info("Finished running seeders!");

    }
}

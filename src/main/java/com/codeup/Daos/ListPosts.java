package com.codeup.Daos;

import com.codeup.Repositories.PostsWithLists;
import com.codeup.models.Post;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Fer on 1/5/17.
 */
public class ListPosts implements PostsWithLists {
    private List<Post> posts;

    public  ListPosts(){
        posts = new ArrayList<>();
    }

    @Override
    public List<Post> all() {
        return posts;
    }

    @Override
    public void save(Post post) {
        post.setId(posts.size()+1);
        posts.add(post);
    }

    @Override
    public void update(Post p) {

    }

    @Override
    public Post getById(int id) {
        return null;
    }

    @Override
    public List<Post> generatePosts() {
        posts.add(new Post(
                1,
                "First post",
                "Gastropub edison bulb snackwave man bun, meggings craft beer tilde readymade vaporware ramps single-origin coffee DIY roof party. Artisan slow-carb kitsch, knausgaard 3 wolf moon street art live-edge sartorial meh DIY skateboard before they sold out tote bag. Ennui meggings subway tile narwhal, single-origin coffee gentrify PBR&B organic mustache vice chartreuse succulents master cleanse. Chartreuse tumblr meggings, pinterest fam cred food truck vape. Flexitarian occupy skateboard, hammock air plant mustache kickstarter shabby chic tofu viral listicle PBR&B gochujang. Tilde salvia health goth activated charcoal ennui brunch. YOLO celiac subway tile asymmetrical, edison bulb lumbersexual everyday carry blog cred etsy paleo enamel pin sustainable."
        ));
        posts.add(new Post(
                2,
                "Another day",
                "Seitan stumptown you probably haven't heard of them master cleanse meditation fingerstache. Pickled kombucha unicorn, chicharrones gluten-free kitsch wolf flannel readymade flexitarian synth affogato vinyl. Vexillologist banh mi polaroid, blog lomo fap waistcoat farm-to-table chicharrones direct trade biodiesel put a bird on it wayfarers irony vinyl. Authentic seitan activated charcoal bicycle rights iceland. Mlkshk forage hoodie, lomo salvia glossier vaporware direct trade gochujang vice. Sriracha quinoa offal vaporware. Green juice artisan hashtag selvage, master cleanse vaporware cardigan la croix selfies crucifix marfa."
        ));
        posts.add(new Post(
                3,
                "New year, new me",
                "Wolf normcore vegan kitsch. Kombucha you probably haven't heard of them skateboard selvage roof party pitchfork. Air plant heirloom vexillologist salvia irony copper mug. Locavore try-hard paleo, polaroid cliche skateboard fap bushwick copper mug. Sartorial yr deep v hashtag, food truck woke skateboard af cliche iPhone. Thundercats kickstarter occupy, skateboard fap single-origin coffee wayfarers leggings. Fanny pack craft beer church-key, raw denim whatever semiotics iceland pinterest iPhone typewriter chillwave pour-over food truck drinking vinegar chia."
        ));
        return posts;
    }

}

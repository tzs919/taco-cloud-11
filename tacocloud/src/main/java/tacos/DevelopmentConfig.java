package tacos;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import tacos.Ingredient.Type;
import tacos.data.IngredientRepository;
import tacos.data.OrderRepository;
import tacos.data.UserRepository;

import java.util.Arrays;

@Profile("!prod")
@Configuration
public class DevelopmentConfig {

    @Bean
    public CommandLineRunner dataLoader(IngredientRepository repo,
                                        UserRepository userRepo, PasswordEncoder encoder, OrderRepository orderRepository) { // user repo for ease of testing with a built-in user
        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {
                Ingredient flourTortilla = new Ingredient("FLTO", "Flour Tortilla", Type.WRAP);
                Ingredient cornTortilla = new Ingredient("COTO", "Corn Tortilla", Type.WRAP);
                Ingredient groundBeef = new Ingredient("GRBF", "Ground Beef", Type.PROTEIN);
                Ingredient carnitas = new Ingredient("CARN", "Carnitas", Type.PROTEIN);
                Ingredient tomatoes = new Ingredient("TMTO", "Diced Tomatoes", Type.VEGGIES);
                Ingredient lettuce = new Ingredient("LETC", "Lettuce", Type.VEGGIES);
                Ingredient cheddar = new Ingredient("CHED", "Cheddar", Type.CHEESE);
                Ingredient jack = new Ingredient("JACK", "Monterrey Jack", Type.CHEESE);
                Ingredient salsa = new Ingredient("SLSA", "Salsa", Type.SAUCE);
                Ingredient sourCream = new Ingredient("SRCR", "Sour Cream", Type.SAUCE);
                repo.save(flourTortilla);
                repo.save(cornTortilla);
                repo.save(groundBeef);
                repo.save(carnitas);
                repo.save(tomatoes);
                repo.save(lettuce);
                repo.save(cheddar);
                repo.save(jack);
                repo.save(salsa);
                repo.save(sourCream);

                User user = new User("habuma", encoder.encode("password"),
                        "Craig Walls", "123 North Street", "Cross Roads", "TX",
                        "76227", "123-123-1234");

                userRepo.save(user);

                Taco taco1 = new Taco();
                taco1.setName("Carnivore");
                taco1.setIngredients(Arrays.asList(flourTortilla, groundBeef, carnitas, sourCream, salsa, cheddar));

                Taco taco2 = new Taco();
                taco2.setName("Bovine Bounty");
                taco2.setIngredients(Arrays.asList(cornTortilla, groundBeef, cheddar, jack, sourCream));

                Taco taco3 = new Taco();
                taco3.setName("Veg-Out");
                taco3.setIngredients(Arrays.asList(flourTortilla, cornTortilla, tomatoes, lettuce, salsa));

                TacoOrder order = new TacoOrder();
                order.setUser(user);
                order.setDeliveryName("deliveryName");
                order.setDeliveryState("deliveryState");
                order.setDeliveryCity("deliveryCity");
                order.setDeliveryStreet("Delivery");
                order.setDeliveryZip("212014");
                order.setCcCVV("123");
                order.setCcExpiration("12/25");
                order.setCcNumber("6317758315000646578");
                order.addTaco(taco1);
                order.addTaco(taco2);
                order.addTaco(taco3);
                orderRepository.save(order);
            }
        };
    }

}

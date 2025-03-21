package hr.vpetrina.webshop.repository;

import hr.vpetrina.webshop.exception.ItemNotFoundException;
import hr.vpetrina.webshop.model.GuitarBody;
import hr.vpetrina.webshop.model.GuitarCategory;
import hr.vpetrina.webshop.model.GuitarItem;
import hr.vpetrina.webshop.model.GuitarNeck;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class GuitarRepositoryImpl implements GuitarRepository {

    private static final List<GuitarItem> mockItems;

    static {
        mockItems = new ArrayList<>(List.of(
                new GuitarItem(
                        1,
                        "Fender American Professional II Stratocaster Electric Guitar",
                        "The Fender American Professional II Stratocaster brings together modern features with" +
                                " classic Fender " +
                                "style. This exceptional instrument is designed to meet the needs of discerning players," +
                                " whether " +
                                "you're rocking on stage or crafting new music in the studio. With its crisp, articulate" +
                                " tones, the " +
                                "Stratocaster's signature sound is perfect for everything from blues to rock and beyond," +
                                " providing an " +
                                "unmatched playing experience.",
                        1499.99,
                        new GuitarBody("Stratocaster", "Alder"),
                        new GuitarNeck("Slim C", 43.00, "Maple", 22),
                        "V-Mod II Single-Coil",
                        GuitarCategory.ELECTRIC,
                        "https://media.guitarcenter.com/is/image/MMGS7/L78115000006000-00-600x600.jpg"
                ),
                new GuitarItem(
                        2,
                        "PRS SE Custom 24 Electric Guitar",
                        "The PRS SE Custom 24 offers incredible versatility and outstanding craftsmanship at" +
                                " an affordable " +
                                "price. With its signature PRS tone and a solid construction, the SE Custom 24 is designed" +
                                " to satisfy " +
                                "players who demand high-performance from their instrument. Featuring a comfortable " +
                                "wide-thin neck and " +
                                "a beautiful flame maple veneer top, this guitar is a great addition to any player's arsenal.",
                        799.00,
                        new GuitarBody("Custom 24", "Mahogany with Flame Maple Veneer"),
                        new GuitarNeck("Wide Thin", 43.00, "Maple", 24),
                        "PRS 85/15 S Humbuckers",
                        GuitarCategory.ELECTRIC,
                        "https://media.guitarcenter.com/is/image/MMGS7/L79262000005000-00-600x600.jpg"
                ),
                new GuitarItem(
                        3,
                        "Ibanez RG550 Electric Guitar",
                        "The Ibanez RG550 is a legendary instrument that's known for its aggressive, " +
                                "high-output sound and ultra-fast " +
                                "playability. With a sleek, sculpted body and a rock-solid neck, the RG550 offers " +
                                "exceptional comfort and " +
                                "precision. Whether you're playing shredding solos or heavy rhythms, this guitar " +
                                "has the tone and sustain " +
                                "you need to take your playing to the next level.",
                        749.99,
                        new GuitarBody("RG", "Basswood"),
                        new GuitarNeck("Super Wizard", 43.00, "Maple", 24),
                        "Quantum Humbuckers",
                        GuitarCategory.ELECTRIC,
                        "https://media.guitarcenter.com/is/image/MMGS7/K59743000001000-00-600x600.jpg"
                ),
                new GuitarItem(
                        4,
                        "Gibson Custom Eric Clapton 1958 Les Paul Custom Electric Guitar Ebony",
                        "The Gibson Eric Clapton 1958 Les Paul Custom limited-edition electric guitar is a time machine, " +
                                "a conduit to a golden era of music and a testament to the enduring power of legendary instruments. " +
                                "This masterpiece captures the soul of Clapton's iconic 1958 Les Paul Custom, meticulously " +
                                "recreated by the artisans of the Gibson Custom Shop and the Murphy Lab. Every detail, from the" +
                                " aged patina to the resonant voice, whispers tales of countless stages and legendary " +
                                "recordings, offering a rare opportunity to hold a piece of music history in your hands.",
                        19999.00,
                        new GuitarBody("Les Paul", "One-piece ultralight weight mahogany"),
                        new GuitarNeck("Medium C", 42.86, "Mahogany", 22),
                        "Humbuckers",
                        GuitarCategory.ELECTRIC,
                        "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSnO-IEiFOkA1hDbsrasi5sSGeEPbTWW5L-xg&s"
                )
        ));
    }

    @Override
    public List<GuitarItem> getAll() {
        return mockItems;
    }

    @Override
    public Optional<GuitarItem> getById(Integer id) {
        return mockItems.stream().filter(guitarItem -> guitarItem.getId().equals(id)).findFirst();
    }

    @Override
    public GuitarItem insert(GuitarItem item) {
        item.setId(generateNewId());
        mockItems.add(item);
        return item;
    }

    private Integer generateNewId() {
        Optional<Integer> maxId = mockItems.stream()
                .map(GuitarItem::getId)
                .max(Integer::compareTo);

        return maxId.map(integer -> integer + 1).orElse(1);
    }

    @Override
    public Optional<GuitarItem> update(Integer id, GuitarItem item) {
        Optional<GuitarItem> inventoryItemOptional = getById(id);

        if (inventoryItemOptional.isPresent()) {
            GuitarItem guitarItem = inventoryItemOptional.get();
            guitarItem.setId(id);
            guitarItem.setTitle(item.getTitle());
            guitarItem.setDescription(item.getDescription());
            guitarItem.setPrice(item.getPrice());
            guitarItem.setPickups(item.getPickups());
            guitarItem.setNeck(item.getNeck());
            guitarItem.setBody(item.getBody());

            return Optional.of(guitarItem);
        }

        return Optional.empty();
    }

    @Override
    public void delete(Integer id) {
        Optional<GuitarItem> guitarItemOptional = getById(id);

        if (guitarItemOptional.isPresent()) {
            mockItems.remove(guitarItemOptional.get());
        }
        else {
            throw new ItemNotFoundException("Item with provided id does not exist");
        }
    }

    @Override
    public List<GuitarItem> filterByName(String query) {
        return mockItems
                .stream()
                .filter(item -> item.getTitle().contains(query))
                .toList();
    }

    @Override
    public List<GuitarItem> getFilteredGuitars(GuitarCategory category, String query) {
        return mockItems.stream()
                .filter(item -> (query == null || query.isEmpty() || item.getTitle().toLowerCase().contains(query.toLowerCase())))
                .filter(item -> (category == null || item.getCategory().equals(category)))
                .toList();
    }

}

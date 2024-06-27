// Singleton
class OrderManager {
    // Єдиний екземпляр класу OrderManager
    private static OrderManager instance;

    // Приватний конструктор для запобігання створення об'єктів зовні
    private OrderManager() {
        // Приватний конструктор
    }

    // Метод для отримання єдиного екземпляру класу
    public static synchronized OrderManager getInstance() {
        // Якщо екземпляр ще не створено, створюємо його
        if (instance == null) {
            instance = new OrderManager();
        }
        // Повертаємо єдиний екземпляр
        return instance;
    }

    // Інші методи для управління замовленнями можуть бути додані тут
}

// Bridge - Абстракція
abstract class Order {
    // Збереження реалізації інтерфейсу OrderImplementation
    protected OrderImplementation implementation;

    // Конструктор з прийняттям реалізації
    protected Order(OrderImplementation implementation) {
        this.implementation = implementation;
    }

    // Абстрактний метод для розміщення замовлення
    public abstract void placeOrder();

    // Абстрактний метод для прийняття візитора
    public abstract void accept(OrderVisitor visitor);
}

// Bridge - Реалізація
interface OrderImplementation {
    // Метод для розміщення замовлення
    void placeOrder();
}

// Bridge - Конкретна реалізація для їжі
class FoodOrder implements OrderImplementation {
    // Метод для розміщення замовлення на їжу
    @Override
    public void placeOrder() {
        System.out.println("Placing food order.");
    }
}

// Bridge - Конкретна реалізація для напоїв
class DrinkOrder implements OrderImplementation {
    // Метод для розміщення замовлення на напої
    @Override
    public void placeOrder() {
        System.out.println("Placing drink order.");
    }
}

// Bridge - Конкретна абстракція для замовлення їжі
class Food extends Order {
    // Конструктор, що приймає реалізацію OrderImplementation
    public Food(OrderImplementation implementation) {
        super(implementation);
    }

    // Метод для розміщення замовлення на їжу
    @Override
    public void placeOrder() {
        implementation.placeOrder();
    }

    // Метод для прийняття візитора
    @Override
    public void accept(OrderVisitor visitor) {
        visitor.visit(this);
    }
}

// Bridge - Конкретна абстракція для замовлення напоїв
class Drink extends Order {
    // Конструктор, що приймає реалізацію OrderImplementation
    public Drink(OrderImplementation implementation) {
        super(implementation);
    }

    // Метод для розміщення замовлення на напої
    @Override
    public void placeOrder() {
        implementation.placeOrder();
    }

    // Метод для прийняття візитора
    @Override
    public void accept(OrderVisitor visitor) {
        visitor.visit(this);
    }
}

// Visitor - Інтерфейс для візитора
interface OrderVisitor {
    // Метод для візиту до замовлення їжі
    void visit(Food food);

    // Метод для візиту до замовлення напоїв
    void visit(Drink drink);
}

// Visitor - Конкретний візитор для обрахування вартості замовлення
class CostCalculator implements OrderVisitor {
    // Метод для обрахування вартості замовлення їжі
    @Override
    public void visit(Food food) {
        System.out.println("Calculating cost for food.");
    }

    // Метод для обрахування вартості замовлення напоїв
    @Override
    public void visit(Drink drink) {
        System.out.println("Calculating cost for drink.");
    }
}

// Головний клас програми
public class Main {
    public static void main(String[] args) {
        // Отримання єдиного екземпляру OrderManager
        OrderManager orderManager = OrderManager.getInstance();

        // Створення замовлення на їжу з конкретною реалізацією FoodOrder
        Order foodOrder = new Food(new FoodOrder());

        // Створення замовлення на напої з конкретною реалізацією DrinkOrder
        Order drinkOrder = new Drink(new DrinkOrder());

        // Розміщення замовлення на їжу
        foodOrder.placeOrder();

        // Розміщення замовлення на напої
        drinkOrder.placeOrder();

        // Створення візитора для обрахування вартості замовлень
        OrderVisitor costCalculator = new CostCalculator();

        // Прийняття візитора для обрахування вартості замовлення їжі
        foodOrder.accept(costCalculator);

        // Прийняття візитора для обрахування вартості замовлення напоїв
        drinkOrder.accept(costCalculator);
    }
}

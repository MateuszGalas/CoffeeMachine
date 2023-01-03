package machine

enum class Ingredients (val water: Int, val milk: Int, val coffee: Int, val price: Int) {
    ESPRESSO(250, 0, 16, 4),
    LATTE(350,75, 20, 7),
    CAPPUCCINO(200, 100, 12, 6)
}

class CoffeeMachine() {
    private var waterInMachine: Int = 400
    private var milkInMachine: Int = 540
    private var coffeeInMachine: Int = 120
    private var disposableCups = 9
    private var money = 550

    private val espresso = Ingredients.ESPRESSO
    private val latte = Ingredients.LATTE
    private val cappuccino = Ingredients.CAPPUCCINO

    private fun status() {
        println("The coffee machine has:")
        println("$waterInMachine ml of water")
        println("$milkInMachine ml of milk")
        println("$coffeeInMachine g of coffee beans")
        println("$disposableCups disposable cups")
        println("$$money of money\n")
    }

    private fun buy() {
        println("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu: ")
        when (readln()) {
            "1" -> changeAmount(espresso)
            "2" -> changeAmount(latte)
            "3" -> changeAmount(cappuccino)
            "back" -> { }
        }
        println()
    }

    private fun changeAmount(coffee: Ingredients) {
        if (waterInMachine - coffee.water < 0) println("Sorry, not enough water!").also { return }
        if (milkInMachine - coffee.milk < 0) println("Sorry, not enough milk!").also { return }
        if (coffeeInMachine - coffee.coffee < 0) println("Sorry, not enough coffee!").also { return }
        if (disposableCups - 1 < 0) println("Sorry, not enough cups!").also { return }
        println("I have enough resources, making you a coffee!")

        waterInMachine -= coffee.water
        milkInMachine -= coffee.milk
        coffeeInMachine -= coffee.coffee
        disposableCups -= 1
        money += coffee.price
    }

    private fun fill() {
        println("Write how many ml of water you want to add:")
        waterInMachine += readln().toInt()
        println("Write how many ml of milk you want to add:")
        milkInMachine += readln().toInt()
        println("Write how many grams of coffee beans you want to add:")
        coffeeInMachine += readln().toInt()
        println("Write how many disposable cups you want to add:")
        disposableCups += readln().toInt()
        println()
    }

    private fun take() {
        println("I gave you $money\n")
        money = 0
    }

    fun run() {
        while (true) {
            println("Write action (buy, fill, take, remaining, exit):")
            when (readln()) {
                "buy" -> buy()
                "fill" -> fill()
                "take" -> take()
                "remaining" -> status()
                "exit" -> break
            }
        }
    }
}

fun main() {
    val machine = CoffeeMachine()
    machine.run()
}

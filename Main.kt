package machine

enum class Ingredients(val water: Int, val milk: Int, val coffee: Int, val price: Int) {
    ESPRESSO(250, 0, 16, 4),
    LATTE(350, 75, 20, 7),
    CAPPUCCINO(200, 100, 12, 6)
}

enum class STATE {
    MENU, BUY, FILL_WATER, FILL_MILK, FILL_COFFEE, FILL_CUPS
}

class CoffeeMachine() {
    private var waterInMachine: Int = 400
    private var milkInMachine: Int = 540
    private var coffeeInMachine: Int = 120
    private var disposableCups = 9
    private var money = 550
    private var state = STATE.MENU

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

    private fun buy(action: String) {
        when (action) {
            "1" -> changeAmount(espresso)
            "2" -> changeAmount(latte)
            "3" -> changeAmount(cappuccino)
            "back" -> {}
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

    private fun take() {
        println("I gave you $money\n")
        money = 0
    }

    private fun getCommand() {
        when (state) {
            STATE.MENU -> println("Write action (buy, fill, take, remaining, exit):")
            STATE.FILL_MILK -> println("Write how many ml of milk you want to add:")
            STATE.FILL_COFFEE -> println("Write how many grams of coffee beans you want to add:")
            STATE.FILL_CUPS -> println("Write how many disposable cups you want to add:")
            STATE.FILL_WATER -> println("Write how many ml of water you want to add:")
            STATE.BUY -> println(
                "What do you want to buy? 1 - espresso, 2 - latte, " +
                        "3 - cappuccino, back - to main menu:"
            )
        }
    }

    fun run() {
        while (true) {
            getCommand()
            val action = readln()
            when (state) {
                STATE.MENU -> {
                    when (action) {
                        "buy" -> state = STATE.BUY
                        "fill" -> state = STATE.FILL_WATER
                        "take" -> take()
                        "remaining" -> status()
                        "exit" -> break
                    }
                }

                STATE.BUY -> {
                    buy(action)
                    state = STATE.MENU
                }

                STATE.FILL_WATER -> {
                    waterInMachine += action.toInt()
                    state = STATE.FILL_MILK
                }

                STATE.FILL_MILK -> {
                    milkInMachine += action.toInt()
                    state = STATE.FILL_COFFEE
                }

                STATE.FILL_COFFEE -> {
                    coffeeInMachine += action.toInt()
                    state = STATE.FILL_CUPS
                }

                STATE.FILL_CUPS -> {
                    disposableCups += action.toInt()
                    println()
                    state = STATE.MENU
                }
            }
        }
    }
}

fun main() {
    val machine = CoffeeMachine()
    machine.run()
}

package com.premierdarkcoffee.sales.hermes.root.feature.chat.domain.model.product

import com.premierdarkcoffee.sales.hermes.root.feature.chat.data.local.entity.product.PriceEntity
import com.premierdarkcoffee.sales.hermes.root.feature.chat.data.remote.dto.product.PriceDto


data class Price(val amount: Double, val currency: String = "USD", val offer: Offer, val creditCard: CreditCard? = null) {

    fun toPriceDto(): PriceDto {
        return PriceDto(amount = amount,
                        currency = currency,
                        creditCard = creditCard?.toCreditCardDto(),
                        offer = offer.toOfferDto())
    }

    fun toPriceEntity(): PriceEntity {
        return PriceEntity(amount = amount,
                           currency = currency,
                           creditCard = creditCard?.toCreditCardEntity(),
                           offer = offer.toOfferEntity())
    }
}

/*
data class ConfigurationDto(val type: ConfigurationType, val options: List<OptionDto>) {
    fun toConfiguration(): Configuration {
        return Configuration(type, options.map { it.toOption() })
    }
}

data class OptionDto(val name: String, val price: Double) {
    fun toOption(): Option {
        return Option(name, price)
    }
}

enum class ConfigurationType {
    CHIP1, CHIP2, CHIP3, CHIP4, CHIP5, CHIP6,
}

data class Configuration(val type: ConfigurationType, val options: List<Option>)

data class Option(val name: String, val price: Double)

@Composable
fun PView() {
    val priceDto = PriceDto(amount = 200.0,
                            currency = "USD",
                            offer = OfferDto(active = true, discount = 20),
                            creditCard = CreditCardDto(withInterest = 12, withoutInterest = 3, freeMonths = 6),
                     configurations = listOf(
                         ConfigurationDto(type = ConfigurationType.CHIP1,
                                          options = listOf(OptionDto("Option one", 0.0), OptionDto("Option two", 10.0))),
                         ConfigurationDto(type = ConfigurationType.CHIP2,
                                          options = listOf(OptionDto("Option one", 0.0), OptionDto("Option two", 200.0))),
                         ConfigurationDto(type = ConfigurationType.CHIP3,
                                          options = listOf(OptionDto("Option one", 0.0), OptionDto("Option two", 200.0))),
                         ConfigurationDto(type = ConfigurationType.CHIP4,
                                          options = listOf(OptionDto("Option one", 0.0), OptionDto("Option two", 200.0))),
                                             ConfigurationDto(type = ConfigurationType.CHIP5,
                                                              options = listOf(OptionDto("Option one", 0.0),
                                                                               OptionDto("Option two", 200.0))),
                                             ConfigurationDto(type = ConfigurationType.CHIP6,
                                                              options = listOf(OptionDto("Option one", 0.0),
                                                                               OptionDto("Option two", 200.0)))
                     )
    )

    var selectedOptions by remember { mutableStateOf(priceDto.configurations.associate { it.type to it.options.first() }) }
    var totalPrice by remember { mutableStateOf(priceDto.amount + selectedOptions.values.sumOf { it.price }) }

    val scrollState = rememberScrollState()

    Column(modifier = Modifier
        .padding(16.dp)
        .verticalScroll(scrollState)) {
        Text("Price: ${priceDto.currency} ${String.format("%.2f", totalPrice)}", fontSize = 20.sp, fontWeight = FontWeight.Bold)

        if (priceDto.offer.active) {
            Text("Discount: ${priceDto.offer.discount}%", color = Color.Green)
        }

        priceDto.creditCard?.let { creditCard ->
            Text("Credit Card Options:", fontWeight = FontWeight.Bold)
            Text("Without Interest: ${creditCard.withoutinterest} months")
            Text("With Interest: ${creditCard.withinterest} months")
            Text("Free Months: ${creditCard.freeMonths}")
        }

        priceDto.configurations.forEach { configuration ->
            ConfigurationView(configuration = configuration,
                              selectedOptions = selectedOptions,
                              totalPrice = totalPrice,
                              baseAmount = priceDto.amount) { selectedOption ->
                selectedOptions = selectedOptions.toMutableMap().apply { put(configuration.type, selectedOption) }
                totalPrice = priceDto.amount + selectedOptions.values.sumOf { it.price }
            }
        }
    }
}

@Composable
fun ConfigurationView(configuration: ConfigurationDto,
                      selectedOptions: Map<ConfigurationType, OptionDto>,
                      totalPrice: Double,
                      baseAmount: Double,
                      onOptionSelected: (OptionDto) -> Unit) {
    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        Text(configuration.type.name.lowercase()
                 .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() },
             fontWeight = FontWeight.Bold)

        configuration.options.forEach { option ->
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
                .clickable { onOptionSelected(option) }
                .background(if (selectedOptions[configuration.type] == option) Color.LightGray else Color.Transparent,
                            shape = RoundedCornerShape(8.dp))
                .padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                Text(option.name, fontSize = 16.sp)
                Spacer(modifier = Modifier.weight(1f))
                Text("+ $${String.format("%.2f", option.price)}", fontSize = 16.sp, color = Color.Gray)
            }
        }
    }
}
*/
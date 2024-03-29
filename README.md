<p align="center">
    <img src="https://raw.githubusercontent.com/reducktion/socrates-java/master/docs/logo.png" alt="Socrates logo" width="480">
</p>
<p align="center">
    <img src="https://raw.githubusercontent.com/reducktion/socrates-java/master/docs/example.png" alt="Usage example" width="800">
</p>
<p align="center">
    <img alt="Build" src="https://github.com/reducktion/socrates-java/workflows/Build/badge.svg?branch=master">
    <img alt="License" src="https://img.shields.io/github/license/reducktion/socrates-java">
    <img alt="Maven Central" src="https://img.shields.io/maven-central/v/com.github.reducktion/socrates-java">
</p>

---
## Introduction
This package is a port of the [php package socrates](https://github.com/reducktion/socrates).

socrates-java allows you to validate and retrieve personal data from National Identification Numbers across the world, 
with the goal of eventually supporting as many countries in the world as possible.

Some countries also encode personal information of the citizen, such as gender or the place of birth. This package allows you to extract that information in 
a consistent way.

It can be useful for many things, such as validating a user's ID for finance related applications or verifying a user's age without asking for it explicitly.
However, we recommend you review your country's data protection laws before storing any information.

## Usage
Socrates provides three methods:
* `validateId`, which returns a boolean indicating if an id is valid in a specific country
* `extractCitizenFromId`, which returns an Optional `Citizen` with data retrievable from the id (gender, date of birth, ...)
* `generateId`, which returns an id generated based on the `Citizen` data and country

You can find a list of supported countries [here](COUNTRIES.md).

### validateId
```java
final Socrates socrates = new Socrates();

socrates.validateId("15420355 6 ZX9", Country.PT); // true
```

### extractCitizenFromId
```java
final Socrates socrates = new Socrates();

final Optional<Citizen> citizen = socrates.extractCitizenFromId("2820819398814 09", Country.FR);
citizen.ifPresent(c -> {
  c.getGender().ifPresent(System.out::println);       // "FEMALE"
  c.getYearOfBirth().ifPresent(System.out::println);  // "1982"
  c.getMonthOfBirth().ifPresent(System.out::println); // "8"
  c.getDayOfBirth().ifPresent(System.out::println);
  c.getPlaceOfBirth().ifPresent(System.out::println); // "Corrèze"
});
```

### generateId
```java
final Socrates socrates = new Socrates();
final Citizen citizen = Citizen.builder()
        .yearOfBirth(1984)
        .monthOfBirth(10)
        .dayOfBirth(8)
        .gender(Gender.FEMALE)
        .build();

final String nationalId = socrates.generateId(citizen, Country.DK); // "081084-3012"
```

Alternatively, you can use interfaces and classes that are directly available. However, note that this can introduce a lot of unwanted dependencies in your code base.

#### Gender
`Gender` is an enum that represents the gender of the `Citizen` extracted from the ID. It can have the values `FEMALE` or `MALE`. However, you can get the
short hand "F" and "M" for `FEMALE` and `MALE` respectively, by using the method `getShortHand()` present in the class.

#### 

## Contributing
Did you find a problem in any of the algorithms? 
Do you know how to implement a country which we have missed?
Are there any improvements that you think should be made to the codebase?
Any help is appreciated! Take a look at our [contributing guidelines](CONTRIBUTING.md).

## License
The MIT License (MIT). Please see [License File](LICENSE.md) for more information.

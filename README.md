# Advent of Code 2023

## Requires

[Apache Maven](https://maven.apache.org)

## Usage

```ssh
mvn [clean compile] exec:java@App -Dexec.args="[see below]"
```

- clean compile can be omitted if already compiled
- Flags:
  - Parts: -[1][2]
  - Test-Input: [-t] (Beware: Not every test input exists, see resources/.../test_input/)
  - Days: enter days separated by spaces without leading zeroes


## Example 

Compile and execute parts one and two of day 4 and 7 with test input:

```ssh
mvn clean compile exec:java@App -Dexec.args="-12 -t 4 7"
```

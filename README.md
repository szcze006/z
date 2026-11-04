# Java Synchronization Lab

Open this repository folder in IntelliJ IDEA as a Java project. All files are in the `src` directory.

## Voting booth tests

Open `src/VoteLab.java`, then choose **Run 'VoteLab.main()'**. Set its **Program arguments** in **Run > Edit Configurations** to choose one test:

| Program arguments | Experiment |
|---|---|
| `1 2000` | No acquire or release |
| `2 2000` | One permit with acquire and release |
| `3 2000` | Two permits |
| `4 2000` | Release without acquire |
| `5 2000` | Acquire without release |
| `6 2000` | Acquire and release plus cyclic barrier |

The first argument is test number; the second is milliseconds to run. Omit the second argument for ten seconds. The sample output is nondeterministic. Test 5 shows blocked threads; this version interrupts them after the test duration so it exits cleanly.

## Producer-consumer tests

Open `src/Factory.java` and run **Factory.main()** with these program arguments:

| Program arguments | Experiment |
|---|---|
| `2 1 3` | Original configuration: capacity 2, 1 producer, 3 consumers |
| `10 1 3` | Larger buffer |
| `10 3 1` | Three producers, one consumer |
| `10 1 3` | One producer, three consumers |

The order is **buffer size, number of producers, number of consumers**. Factory runs continuously like the original assignment; click the red Stop square in IntelliJ to end it.

The code here is a **runnable adaptation** of the uploaded exercises, with selectable test modes, identifiable worker names, and an interruptible voting run. It is not a byte-for-byte copy of the Canvas files.

## Screenshot tips

For each test, show the IntelliJ editor and **Run** output together. Capture a Mac screenshot with **Command + Shift + 4**. Keep the selected program arguments visible if possible.

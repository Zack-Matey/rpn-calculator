# RPN Calculator - Zack Matey
RPN Calculator by Zack Matey for COS 470 Spring 2021

## Iteration 3 Operations
For Iteration 3 I implemented three new operations:
    "signed" sets the mode of the calculator to signed.
    "unsigned" sets the mode of the calculator to unsigned.
    "+-" swaps the mode of the calculator - if it was unsigned it becomes signed, 
        if it was signed it becomes unsigned.

These three operators, combined with the boolean in the initialization,
are used to run the calculator with signed or unsigned logic.

## Iteration 4 Operations
For Iteration 3 I implemented the ability to push and pop from the calculator in
different bases and formats.
Binary: 0b0101
Octal: 0768
Decimal (previously supported): 1029
Roman Numerals (up to 3999): IX

Numbers can be pushed in any of these formats and are parsed.
Note that in pushing negation can be represented before or after the
prefix, or both, at which point it cancels out.
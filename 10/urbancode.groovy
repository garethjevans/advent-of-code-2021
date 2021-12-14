input = new File('real-data.txt').text.split("\n").collect{ Arrays.asList(it.trim().toCharArray()) }

startingChars = ["(","[","{","<"]
endChars = ["(":")","[":"]","{":"}","<":">"]
values = [")": 3, "]": 57, "}": 1197, ">": 25137]

endValues = [")":1, "]":2, "}":3, ">":4]

failures = []
incompleteTotals = []

input.each{ row ->
    stack = []

    failedChar = ""

    for (int i=0; i<row.size(); i++) {
        c = Character.toString(row[i])
        if (c in startingChars) {
            stack.push(c)
        } else {
            toCheck = stack.pop()
            if (c != endChars[toCheck] ) {
                println "Row ${row} failed on char ${c}"
                failedChar = c
                break
            }
        }
    }

    if (failedChar != "" ) {
        failures << values[failedChar]
    } else if (stack.size() > 0) {
        println "Row ${row} is incomplete"

        //println "stack left = ${stack}"
        toComplete = stack.collect{ endChars[it] }
        println toComplete

        toCompleteValues = toComplete.collect{ endValues[it] }
        total = 0L
        toCompleteValues.each{ v -> 
            total = total*5
            total += v
        }

        incompleteTotals << total
    } 
}

println failures.sum()

println incompleteTotals.sort()
println incompleteTotals.sort()[incompleteTotals.size()/2]

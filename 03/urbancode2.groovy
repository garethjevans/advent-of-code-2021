
String.metaClass.asBits = {
    delegate.toCharArray().collect{c -> Integer.parseInt("${c}")} 
}

class Record {
    int[] bits

    String toString() {
        bits.join()
    }
}

class Result {
    String epsilonRate
    String gammaRate

    int total() {
        def e = Integer.parseInt(epsilonRate, 2);
        def g = Integer.parseInt(gammaRate, 2);
        return e * g
    }
}

records = new File('test-data.txt').text.split('\n').collect{ new Record(bits: it.asBits()) }
realRecords = new File('real-data.txt').text.split('\n').collect{ new Record(bits: it.asBits()) }


def getOxygenGeneratorRating(records) {
    filtered = records

    for (int i=0; i<records[0].bits.length; i++) {
        numOnes = filtered.sum{ it.bits[i] }
        numZeros = filtered.size() - numOnes

        if (numOnes != numZeros) {
            filtered = filtered.findAll{ it.bits[i] == (numOnes > numZeros ? 1 : 0) }
            println "Pass ${i}, filtered to ${filtered}"
        } else {
            println "Pass ${i}, keeping 1"
            filtered = filtered.findAll{ it.bits[i] == 1 }
        }

        if (filtered.size() == 1) {
            break;
        }
    }
    return filtered[0].toString()
}


def getC02ScrubberGeneratorRating(records) {
    filtered = records

    for (int i=0; i<records[0].bits.length; i++) {
        numOnes = filtered.sum{ it.bits[i] }
        numZeros = filtered.size() - numOnes

        if (numOnes != numZeros) {
            filtered = filtered.findAll{ it.bits[i] == (numOnes < numZeros ? 1 : 0) }
            println "Pass ${i}, filtered to ${filtered}"
        } else {
            println "Pass ${i}, keeping 0"
            filtered = filtered.findAll{ it.bits[i] == 0 }
        }

        if (filtered.size() == 1) {
            break;
        }
    }
    return filtered[0].toString()
}


oxygenGeneratorRating = getOxygenGeneratorRating(records)
assert Integer.parseInt(oxygenGeneratorRating.toString(), 2) == 23

co2ScrubberRating = getC02ScrubberGeneratorRating(records)
assert Integer.parseInt(co2ScrubberRating.toString(), 2) == 10

oxygenGeneratorRating = getOxygenGeneratorRating(realRecords)
ogValue = Integer.parseInt(oxygenGeneratorRating.toString(), 2)

co2ScrubberRating = getC02ScrubberGeneratorRating(realRecords)
coValue = Integer.parseInt(co2ScrubberRating.toString(), 2)

println ogValue * coValue


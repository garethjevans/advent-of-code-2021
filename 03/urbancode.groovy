
String.metaClass.asBits = {
    delegate.toCharArray().collect{c -> Integer.parseInt("${c}")} 
}

class Record {
    int[] bits

    String toString() {
        "${Arrays.asList(bits)}"
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

def calculate(records) {
    def epsilonRate = []
    def gammaRate = []
    def total = records.size()
    for (int i=0; i<records[0].bits.length; i++) {
        count = records.sum{ it.bits[i] }
        epsilonRate << (count <= total/2 ? 1 : 0)
        gammaRate << (count <= total/2 ? 0 : 1)
    }
    return new Result(epsilonRate: epsilonRate.join(), gammaRate: gammaRate.join())
}

testResult = calculate(records)

assert testResult.total() == 198
println testResult.total()

realResult = calculate(realRecords)
println realResult.total()


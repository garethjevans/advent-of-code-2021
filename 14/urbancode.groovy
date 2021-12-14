class Polymer {
    def Map rules = [:]
    def Map counters = [:].withDefault{ 0L }
    def Map charCounters = [:]

    def Polymer(String file) {
        def lines = new File(file).text.split('\n')
        def string = lines[0].toCharArray().collect{ "${it}".toString() }
        
        (1..string.size()-1).each{ i -> 
            def pair = string[i-1] + string[i]
            counters[pair] = 1L
        }

        charCounters = string.countBy{ it }.withDefault{ 0L }
        rules = lines.toList().subList(2, lines.length).collectEntries{[(it.split(' -> ')[0]): it.split(' -> ')[1]]}
    }

    def void expand() {
        // effectively a clone
        def clone = counters.collectEntries{ it }.withDefault{ 0L }
        
        def keys = counters.keySet()
        (0..keys.size()-1).each { i -> 
            def k = keys[i]
            def v = counters[k]
            def newChar = rules[k]

            if (newChar && v > 0) {
                charCounters[newChar] += v.toLong()

                def left = k[0] + newChar
                def right = newChar + k[1]
              
                clone[k] -= v.toLong()
                clone[left] += v.toLong()
                clone[right] += v.toLong()
            }
        }

        counters = clone
    }

    def void expandN(int times) {
        (1..times).each{ i -> 
            expand()  
        }
    }

    def Long diffBetweenMaxAndMin() {
        def max = charCounters.values().max()
        def min = charCounters.values().min()
        return max - min
    }
}


p = new Polymer('test-data.txt')
p.expand()
assert 7 == p.charCounters.values().sum()
p.expand()
assert 13 == p.charCounters.values().sum()
p.expand()
assert 25 == p.charCounters.values().sum()

p.expandN(7)
assert 3073 == p.charCounters.values().sum()
assert 1588 == p.diffBetweenMaxAndMin()
p.expandN(30)
assert 2188189693529L == p.diffBetweenMaxAndMin()

p = new Polymer('real-data.txt')
p.expandN(10)
println "Part 1: ${p.diffBetweenMaxAndMin()}"
assert 3284 == p.diffBetweenMaxAndMin()
p.expandN(30)
println "Part 2: ${p.diffBetweenMaxAndMin()}"
assert 4302675529689L == p.diffBetweenMaxAndMin()

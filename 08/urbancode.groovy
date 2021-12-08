String.metaClass.toCharList = {
    delegate.toCharArray().collect{ it }
}

records = new File('real-data.txt').text.trim().split('\n').collect{ [input: it.split('\\|')[0].trim().split(' '), output: it.split('\\|')[1].trim().split(' ')] }
println "=========================="
println "Part1: " + records.collect{ row -> row.output.findAll{ it.length() in [2,3,4,7] }.size() }.sum()
println "=========================="
println "Part2: " + records.collect{ row -> 
    one = row.input.find{ it.length() == 2 }?.toCharList() ?: []
    four = row.input.find{ it.length() == 4 }?.toCharList() ?: []

    row.output.collect{ r ->
        if (r.length() == 2) {
            return "1"
        } else if (r.length() == 3 ) {
            return "7"
        } else if (r.length() == 4 ) {
            return "4"
        } else if (r.length() == 7) {
            return "8"
        } else if (r.length() == 6) {
           if (r.toCharList().minus(four).size() == 2 ) {
                return "9"
           } else if (r.toCharList().minus(one).size() == 4) {
                return "0"
           }
           return "6"
        } else if (r.length() == 5) {
            if (r.toCharList().minus(one).size() == 3 ) {
                return "3"
            } else if (r.toCharList().minus(four).size() == 2) {
                return "5"
            }
            return "2"
        }
        // should fail
        return "x"
    }.join()
}.collect{ Integer.parseInt(it) }.sum()
println "=========================="

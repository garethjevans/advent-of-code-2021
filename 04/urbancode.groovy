String.metaClass.toBoard = { ->
    l = delegate.split('\n') 
    b = new Board()
    assert l.size() == 5
    l.eachWithIndex{ it, index -> 
        b.numbers[index] = it.toNumberArray() 
    }
    return b
}

String.metaClass.toNumberArray = { ->
    return delegate.split().collect{ new Number(value: Integer.parseInt(it)) }
}

class Board {
    Number[][] numbers = new Number[5][5]

    void play(val) {
        for (int i=0; i<5; i++ ) {
            for (int j=0; j<5; j++ ) {
               if (numbers[i][j].value == val) {
                    numbers[i][j].marked = true
               } 
            }
        }
    }

    boolean winner() {
        for(int row=0; row<5; row++) {
            int count = 0
            for(int col=0; col<5; col++) {
                if (numbers[row][col].marked) {
                    count++
                }
            }
            if (count == 5) {
                return true
            }
        } 

        for(int row=0; row<5; row++) {
            int count = 0
            for(int col=0; col<5; col++) {
                if (numbers[col][row].marked) {
                    count++
                }
            }
            if (count == 5) {
                return true
            }
        }

        return false
    }

    int sumOfUnplayedNumbers() {
        numbers.sum{ it.sum{ it.marked ? 0 : it.value } }
    }

    String toString() {
        numbers.collect{ it.toString() }.join('\n')
    }

}

class Number {
    int value
    boolean marked

    String toString() {
        marked ? "*${value}*" : "${value}"
    }
}

lines = new File('real-data.txt').text.split('\n').findAll{ it != "" }
numbersToPlay = lines[0].split(',').collect{ Integer.parseInt(it) }

numBoards = (lines.size()-1)/5

boards = []
for (int i=0; i < numBoards; i++) {
    boards << lines.subList((i*5)+1,(i*5+6)).join('\n').toBoard()
}

println "Got ${boards.size} board(s)"

println "Part 1"

outerloop:
for (int i=0; i < numbersToPlay.size(); i++) {
    for (int j=0; j < boards.size(); j++) {
        boards[j].play(numbersToPlay[i])
        if (boards[j].winner()) {
            println "We have a winner!"
            println boards[j]
            //assert 188 == boards[j].sumOfUnplayedNumbers()
            //assert 4512 == numbersToPlay[i] * boards[j].sumOfUnplayedNumbers()
            println numbersToPlay[i] * boards[j].sumOfUnplayedNumbers()
            break outerloop
        }
    }
}

println "Part 2"

boards = []
for (int i=0; i < numBoards; i++) {
    boards << lines.subList((i*5)+1,(i*5+6)).join('\n').toBoard()
}

println "Got ${boards.size} board(s)"

secondouterloop:
for (int i=0; i < numbersToPlay.size(); i++) {
    for (int j=0; j < boards.size(); j++) {
        println "Playing ${numbersToPlay[i]}"

        int before = boards.findAll{ !it.winner() }.size()
        boards[j].play(numbersToPlay[i])
        int after = boards.findAll{ !it.winner() }.size()

        if (before == 1 && after == 0) {
            println "${boards[j]} is the last to win"
            
            //assert numbersToPlay[i] == 13
            //assert boards[j].sumOfUnplayedNumbers() * numbersToPlay[i] == 1924

            println boards[j].sumOfUnplayedNumbers() * numbersToPlay[i]
            break secondouterloop
        }
    } 
}

input = new File('real-data.txt').text.split('\n').collect{ it.toCharArray().collect{ i -> Integer.parseInt("${i}") } }
println input

//input = [[1,1,1,1,1],[1,9,9,9,1],[1,9,1,9,1],[1,9,9,9,1],[1,1,1,1,1]]

class Grid {
    def FLASH = 10
    def EDGE = -1
    def grid = []
    def flashed = []

    void init(input) {
        grid = []
        grid << (0..input.size()+1).collect{ EDGE }
        input.each{ row ->
            def r = []
            r << EDGE
            row.each{ r << it }
            r << EDGE
            grid << r
        }
        grid << (0..input.size()+1).collect{ EDGE }
    }

    void increment() {
        for (int x=1; x<grid.size()-1; x++) {
            for (int y=1; y<grid[x].size()-1; y++) {
                grid[x][y]++
            }
        }
    }

    void bump(int x, int y) {
        if (grid[x][y] != EDGE) {
            grid[x][y]++

            if (grid[x][y] > 9) {
                if (!flashed.contains("${x},${y}")) {
                    flashed << "${x},${y}"
                    bumpAdjacent(x,y)
                }
            } 
        }
    }

    void bumpAdjacent(int x, int y) {
        bump(x-1,y-1)
        bump(x-1,y)
        bump(x-1,y+1)
        bump(x,y-1)
        bump(x,y+1)
        bump(x+1,y-1)
        bump(x+1,y)
        bump(x+1,y+1)
    }

    void flash() {
        for (int x=1; x<grid.size()-1; x++) {
            for (int y=1; y<grid[x].size()-1; y++) {
                if (grid[x][y] > 9) {
                    bump(x,y)
                }
            }
        }
    }

    int countFlashes() {
        int c = 0
        for (int x=1; x<grid.size()-1; x++) {
            for (int y=1; y<grid[x].size()-1; y++) {
                if (grid[x][y] >= FLASH) {
                    c++
                }
            }
        }
        c
    }

    void resetFlashes() {
        flashed = []
        for (int x=1; x<grid.size()-1; x++) {
            for (int y=1; y<grid[x].size()-1; y++) {
                if (grid[x][y] >= FLASH) {
                    grid[x][y] = 0
                }
            }
        }
    }

    String toString() {
        grid.collect{ r -> r.collect{ it == -1 ? "" : "$it" }.join() }.join('\n')
    }
}

g = new Grid()
g.init(input)

println "=========== Before Steps ==========="
println g

flashes = (1..100).collect{ step -> 
    println "=========== Step ${step} ==========="
    g.increment()
    g.flash()
    def f = g.countFlashes()
    g.resetFlashes()
    println g
    f
}

//assert flashes.sum() == 1656

for (int step=101; step<1000; step++) {

    println "=========== Step ${step} ==========="
    g.increment()
    g.flash()
    def f = g.countFlashes()
    g.resetFlashes()
    println g

    if (f == 100) {
        println "Part1: " + flashes.sum()
        println "Part2: ${step}"
        break
    }
}



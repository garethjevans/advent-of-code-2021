class Coord {
    int x
    int y

    String toString() {
        "[${x},${y}]"
    }

    boolean equals(Object other) {
        if (other instanceof Coord) {
            def c = (Coord)other
            return c.x == x && c.y == y
        }
        return false
    }
}

class Paper {
    def dots = []
    def folds = []

    Paper init(String file) {
        def raw = new File(file).text.split('\n')
        folds = raw.findAll{ it.startsWith('fold along') }
        dots = raw.findAll{ it.contains(',') }.collect{ new Coord(x: Integer.parseInt(it.split(',')[0]), y: Integer.parseInt(it.split(',')[1]))} 
        return this
    }

    void foldY(int yFold) {
        dots.findAll{ it.y > yFold }.each{ d -> d.y = yFold - (d.y - yFold) }
        dots = dots.unique()
    }

    void foldX(int xFold) {
        dots.findAll{ it.x > xFold }.each{ d -> d.x = xFold - (d.x - xFold) }
        dots = dots.unique()
    }

    void foldAll() {
        folds.each{ f -> 
            def dir = f.split("=")[0]
            def value = Integer.parseInt(f.split("=")[1])

            if (dir.endsWith('x')) {
                foldX(value)
            } else {
                foldY(value)
            }
        }
    }

    int visableMarks() {
        dots.size()
    }

    String toString() {
        def maxX = dots.collect{ it.x }.max()
        def maxY = dots.collect{ it.y }.max()
        def grid = []
        (0..maxY+1).each{ y -> 
            grid << []
            (0..maxX+1).each { x -> 
                grid[y][x] = '.'
            }
        }

        dots.each{ d -> grid[d.y][d.x] = '█' }
        return grid.collect{ row -> row.join() }.join('\n') 
    }
}



p = new Paper().init('test-data.txt')
assert 18 == p.visableMarks()
p.foldY(7)
assert 17 == p.visableMarks()
p.foldX(5)
assert 16 == p.visableMarks()

p = new Paper().init('real-data.txt')
p.foldX(655)
assert 729 == p.visableMarks()
println "Part1: " + p.visableMarks()

p = new Paper().init('real-data.txt')
p.foldAll()
println "Part2: \n" + p

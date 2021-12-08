String.metaClass.toLine = { ->
    f = delegate.split(' -> ')
    assert f.size() == 2
    return new Line(x1:Integer.parseInt(f[0].split(',')[0]), 
            y1:Integer.parseInt(f[0].split(',')[1]), 
            x2:Integer.parseInt(f[1].split(',')[0]), 
            y2:Integer.parseInt(f[1].split(',')[1]))
}
class Coord {
    int x
    int y
    String toString() {
        "[${x},${y}]"
    }
}

class Line {
    int x1
    int y1
    int x2
    int y2

    String toString() {
        "Line[${x1}, ${y1}] > [${x2}, ${y2}]"
    }

    Coord[] determineCoordinates() {
        def coords = []
        if (x1 == x2) {
            (y1..y2).each{ y -> coords << new Coord(x:x1, y:y) }
        } else if (y1 == y2) {
            (x1..x2).each{ x -> coords << new Coord(x:x, y:y1) }
        } else {
            def startY = y1
            (x1..x2).each{ x -> coords << new Coord(x:x, y: (y1<y2 ? startY++ : startY--)) }
        }
        return coords
    }
}

assert "1,1 -> 3,3".toLine().determineCoordinates().toString() == [new Coord(x:1,y:1), new Coord(x:2,y:2), new Coord(x:3,y:3)].toString()
assert "3,3 -> 1,1".toLine().determineCoordinates().toString() == [new Coord(x:3,y:3), new Coord(x:2,y:2), new Coord(x:1,y:1)].toString()
assert "1,3 -> 3,1".toLine().determineCoordinates().toString() == [new Coord(x:1,y:3), new Coord(x:2,y:2), new Coord(x:3,y:1)].toString()
assert "3,1 -> 1,3".toLine().determineCoordinates().toString() == [new Coord(x:3,y:1), new Coord(x:2,y:2), new Coord(x:1,y:3)].toString()

class Grid {
    int[][] g

    void init(int x, int y) {
        println "x=${x}, y=${y}"
        g = new int[x+1][y+1]
    }

    String toString() {
        g.collect{ Arrays.asList(it).collect{ it == 0 ? "." : "${it}" }.join() }.join('\n')
    }

    void plot(Line l) {
        println "Plotting $l"
        l.determineCoordinates().each{ g[it.y][it.x]++ }
    }

    int dangerPoints() {
        def count = 0
        for (int x=0; x<g.size(); x++) {
            for (int y=0; y<g[x].size(); y++) {
                if (g[x][y] > 1) {
                    count++
                }
            }
        }
        return count
    }
}

lines = new File('real-data.txt').text.split("\n").collect{ it.toLine() }

int maxX = lines.collect{ Math.max(it.x1, it.x2)}.max()
int maxY = lines.collect{ Math.max(it.y1, it.y2)}.max()

Grid g = new Grid()
g.init(Math.max(maxX, maxY), Math.max(maxX, maxY))

lines.each{ g.plot(it) }

println g

println g.dangerPoints()
assert g.dangerPoints() == 16716


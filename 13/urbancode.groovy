class Paper {
    def dots = []
    def grid = []
    def folds = []

    Paper init(String file) {
        def raw = new File(file).text.split('\n')
        folds = raw.findAll{ it.startsWith('fold along') }
        dots = raw.findAll{ it.contains(',') }.collect{ [ x: Integer.parseInt(it.split(',')[0]), y: Integer.parseInt(it.split(',')[1]) ]} 
        drawGrid()
        return this
    }

    void drawGrid() {
        def maxX = dots.collect{ it.x }.max()
        def maxY = dots.collect{ it.y }.max()

        println "Grid is ${maxX+1} x ${maxY+1}" 
        (0..maxY+1).each{ y -> 
            grid << []
            (0..maxX+1).each { x -> 
                grid[y][x] = '.'
            }
        }

        dots.each{ d -> grid[d.y][d.x] = '#' }
    }

    void foldY(int yFold) {
        def folded = []
        def height = ((grid.size()-1)-yFold) 

        println "Grid was ${grid[0].size()} x ${grid.size()}, folding y at ${yFold}, new grid = ${grid[0].size()} x ${height}" 
        (0..yFold-1).each { y -> 
            folded << []
            (0..grid[y].size()-1).each { x -> 
                folded[y][x] = '.'

                if (grid[y][x] == '#') {
                    folded[y][x] = '#'
                }
       
                int otherY = (2*yFold)-y
                if (grid[otherY][x] == '#') {
                    folded[y][x] = '#'
                }
            }
        }
        grid = folded
    }

    void foldX(int xFold) {
        def folded = []
        def width = (grid[0].size()-1)-xFold
        
        println "Grid was ${grid[0].size()} x ${grid.size()}, folding x at ${xFold}, new grid = ${width} x ${grid.size()}" 
        (0..grid.size()-1).each { y -> 
            folded << []
            (0..xFold-1).each{ x -> 
                folded[y][x] = '.'
                
                if (grid[y][x] == '#') {
                    folded[y][x] = '#'
                }
                
                if (grid[y][2*xFold-x] == '#') {
                    folded[y][x] = '#'
                }
            }
        }

        grid = folded
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
        grid.sum{ row -> row.sum{ it == '#' ? 1 : 0 } }
    }

    String toString() {
        return grid.collect{ row -> row.join() }.join('\n') 
    }
}



p = new Paper().init('test-data.txt')
println p
assert 18 == p.visableMarks()
println "------------------------------"
p.foldY(7)
println p
assert 17 == p.visableMarks()
println "------------------------------"
p.foldX(5)
println p
assert 16 == p.visableMarks()

p = new Paper().init('real-data.txt')
p.foldX(655)
assert 729 == p.visableMarks()

p = new Paper().init('real-data.txt')
p.foldAll()
println p

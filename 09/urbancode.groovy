data = new File('real-data.txt').text.split('\n').collect{ it.trim().toCharArray().collect{ Integer.parseInt( "${it}" ) } }

def findAdjacent(data, x, y) {
    toReturn = []
    if (x > 0) {
        toReturn << data[x-1][y]
    }
    if (y > 0) {
        toReturn << data[x][y-1]
    }
    if (x < data.size()-1) {
        toReturn << data[x+1][y]
    }
    if (y < data[x].size()-1) {
        toReturn << data[x][y+1]
    }
    return toReturn
}

def floodFill(data, x, y, list) {
    int wall = 9

    if (data[x][y] == wall) {
        return -1;
    }

    if (data[x][y] == -1) {
        return -1;
    }

    list << "${x},${y}"
    
    data[x][y] = -1;

    if (x < data.size()-1) {
        floodFill(data, x + 1, y, list)
    }
    if (x > 0) {
        floodFill(data, x - 1, y, list)
    }
    if (y < data[x].size()-1) {
        floodFill(data, x, y + 1, list) 
    }
    if (y > 0) {
        floodFill(data, x, y - 1, list)
    }
}

lowPoints = []
for (int x=0; x<data.size(); x++)  {
  for (int y=0; y<data[x].size(); y++)  {
    testValue = data[x][y]
    adj = findAdjacent(data, x, y)

    if (adj.every{ testValue < it }) {
        lowPoints << testValue
    }
  }
}

data.each{ println it.collect{ it == -1 ? " " : "${it}" }.join() }

//assert 15 == lowPoints.collect{ it+1 }.sum()
println "=========================="
println "Part1: " + lowPoints.collect{ it+1 }.sum()
println "=========================="

basins = []

for (int x=0; x<data.size(); x++)  {
  for (int y=0; y<data[x].size(); y++)  {
    testValue = data[x][y]
    if (testValue != 9 && testValue != -1) {
        list = []
        floodFill(data, x, y, list)
        basins << list.size()
    }
  }
}

data.each{ println it.collect{ it == -1 ? " " : "${it}" }.join() }
top3 = basins.sort().reverse().subList(0,3)
println "Part2: " + (top3[0] * top3[1] * top3[2])
println "=========================="

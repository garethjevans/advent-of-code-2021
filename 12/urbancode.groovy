String.metaClass.isLower = {
    return delegate.toLowerCase() == delegate
}


class Cave {

    def allPaths = []
    def options = []
    boolean visitTwice = false

    def Cave fromFile(String file) {
        options = new File(file).text.split('\n').collect{ [from: it.trim().split('-')[0], to: it.trim().split('-')[1]] }
        return this
    }

    def List dfs(String start) {
        def visited = options.collect{ [it.from, it.to] }.flatten().unique().collectEntries{ [ (it): 0 ]} 
        def path = []
        dfsRecursive(start, visited, path)
        return allPaths
    }

    def canVisit(String to, Map visits) {
        if (!to.isLower()) {
            return true
        } else {
            if (!visitTwice) {
                return visits[to] < 1
            } else {
                def maxNumberOfSmallCaveVisits = visits.max{ it.key.isLower() ? it.value : 0 } 
                if (maxNumberOfSmallCaveVisits.value == 2) {
                    return visits[to] < 1
                }
                return visits[to] < 2
            }
        }
    }

    def void dfsRecursive(String current, Map visited, List path) {
        path << current

        if (current == "end") {
            allPaths << path
            return 
        }

        visited[current]++
      
        def placesToGo = options.findAll{ it.from == current || it.to == current }.collect{ it.from == current ? it.to : it.from }.findAll{ it != "start" }
        placesToGo.each { to -> 
            if (canVisit(to, visited)) {
                dfsRecursive(to, visited.clone(), path.clone())
            }
        }
    }   
}

assert new Cave().fromFile('test-data.txt').dfs("start").size() == 10
assert new Cave().fromFile('test-data2.txt').dfs("start").size() == 19
assert new Cave().fromFile('test-data3.txt').dfs("start").size() == 226
println "Part1: " + new Cave().fromFile('real-data.txt').dfs("start").size()

//new Cave(visitTwice: true).fromFile('test-data.txt').dfs("start").each{ println it } 
assert new Cave(visitTwice: true).fromFile('test-data.txt').dfs("start").size() == 36
assert new Cave(visitTwice: true).fromFile('test-data2.txt').dfs("start").size() == 103
assert new Cave(visitTwice: true).fromFile('test-data3.txt').dfs("start").size() == 3509
println "Part2: " + new Cave(visitTwice: true).fromFile('real-data.txt').dfs("start").size()

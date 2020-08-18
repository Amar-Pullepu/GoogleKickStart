package main

import (
	"bufio"
	"fmt"
	"io"
	"os"
	"strconv"
	"strings"
)

var reader *bufio.Reader

func solve(caseNo int) {
	n, err := strconv.ParseInt(readLine(reader), 10, 32)
	checkError(err)
	nTemps := strings.Split(readLine(reader), " ")
	var sum int
	sumFreqs := make(map[int]int)
	sumFreqs[0] = 1
	var count int64
	for itr := 0; itr < int(n); itr++ {
		curr, err := strconv.ParseInt(nTemps[itr], 10, 32)
		checkError(err)
		sum += int(curr)
		for i := 0; i*i < 1e7; i++ {
			count += int64(sumFreqs[sum-i*i])
		}
		val := sumFreqs[sum]
		sumFreqs[sum] = val + 1
	}
	fmt.Print("Case #" + strconv.Itoa(caseNo) + ": " + strconv.FormatInt(count, 10) + "\n")
}

func main() {
	reader = bufio.NewReaderSize(os.Stdin, 1024*1024)
	t, err := strconv.ParseInt(readLine(reader), 10, 32)
	checkError(err)
	for i := 1; i <= int(t); i++ {
		solve(i)
	}
}

func readLine(reader *bufio.Reader) string {
	str, _, err := reader.ReadLine()
	if err == io.EOF {
		return ""
	}

	return strings.TrimRight(string(str), "\r\n")
}

func checkError(err error) {
	if err != nil {
		panic(err)
	}
}

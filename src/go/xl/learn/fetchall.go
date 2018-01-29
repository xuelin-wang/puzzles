package main

import (
	"fmt"
	"os"
	"time"
	"net/http"
	"io"
	"bufio"
)

func main() {
	start := time.Now()
	ch := make(chan string)
	for _, url := range os.Args[1:] {
		go fetch(url, ch)
	}

	for range os.Args[1:] {
		fmt.Println(<-ch)
	}
	fmt.Printf("time elapsed: %f seconds", time.Since(start).Seconds())
}

func fetch(url string, ch chan<- string) {
	start := time.Now()
	resp, err := http.Get(url)
	if err != nil {
		ch <- fmt.Sprintf("err: %v fetching: %s", err, url)
		return
	}

	//nbytes, err := io.Copy(ioutil.Discard, resp.Body)
	f, err := os.Create("result")
	if err != nil {
		ch <- fmt.Sprintf("Error create file result")
		return
	}
	nbytes, err := io.Copy(bufio.NewWriter(f), resp.Body)
	if err != nil {
		ch <- fmt.Sprint(" reading error: %v", err)
		return
	}
	secs := time.Since(start).Seconds()
	ch <- fmt.Sprintf("Time elapsed: %f seconds, %d bytes for url: %s", secs, nbytes, url)
}

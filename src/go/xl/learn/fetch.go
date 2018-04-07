package main

import (
    "fmt"
    "io/ioutil"
    "net/http"
    "os"
    "time"
    "strings"
)

func normalize(url string) string {
    if strings.HasPrefix(url, "http:") {
	return url
    }
    return "http://" + url
}

func fetch(url string, ch chan string) {
	start := time.Now()
            resp, err := http.Get(normalize(url))
	    if err != nil {
		ch <- fmt.Sprintf("Error fetch url. url=%s, error=%v, durationMs=%d", url, err, time.Since(start)/time.Millisecond)
	        return
	    }
	    defer resp.Body.Close()
	    body, err := ioutil.ReadAll(resp.Body)
	    ch <- fmt.Sprintf("Fetched url. url=%s, bodyLen=%d, durationMs=%d", url, len(body), time.Since(start)/time.Millisecond)
}

func main() {
    ch := make(chan string)
    for _, url := range os.Args[1:] {
	go fetch(url, ch)
    }

    for range os.Args[1:] {
        fmt.Printf("%s\n", <- ch)
    }
}

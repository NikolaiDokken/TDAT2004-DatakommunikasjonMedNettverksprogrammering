#include <iostream>
#include <thread>
#include <vector>
#include <list>
#include <math.h>       /* sqrt */


using namespace std;

int main() {
  int amtThreads = 7 ;
  int startInterval = 0;
  int endInterval = 100;

  vector<thread> threads;
  list<int> primes;
  if(startInterval < 2) {
    startInterval = 2;
  }
  int interval = (endInterval - startInterval) / amtThreads;
  for (int i = 0; i < amtThreads; i++) {
    threads.emplace_back([i, interval, amtThreads, startInterval, endInterval, &primes] {
      // Thread work
      list<int> tempPrimes;
      list<int>::iterator it2 = tempPrimes.begin();
      int low = startInterval + i*interval;
      int high = startInterval + i*interval + interval;
      if (i == amtThreads-1) {
        high = endInterval;
      }
      int flag;
      while (low < high) {
        flag = 0;
        for(int i = 2; i <= sqrt(low); ++i) {
          if (low % i == 0) {
            flag = 1;
            break;
          }
        }
        if (flag == 0) {
          tempPrimes.insert(it2, low);
        }
        ++low;
      }
      primes.splice(primes.end(), tempPrimes);
    });
  }

  for (auto &thread : threads) {
    thread.join();
  }
  primes.sort();
  for (int prime : primes) {
    cout << to_string(prime) + " ";
  }
}

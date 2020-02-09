#include <iostream>
#include <thread>
#include <vector>
#include <bits/stdc++.h>


using namespace std;

// To enable threading runtime checks, change the set()-line in CMakeLists.txt to:
// set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -fsanitize=thread -pthread -std=c++1y -Wall -Wextra")
int main() {
  int amtThreads = 10;
  int startInterval = 0;
  int endInterval = 50;

  vector<thread> threads;
  vector<int> primes = vector<int>();
  if(startInterval < 2) startInterval = 2;
  int interval = (endInterval - startInterval) / amtThreads;
  for (int i = 0; i < amtThreads; i++) {
    threads.emplace_back([i, interval, amtThreads, startInterval, endInterval, &primes] {
      // Thread work
      // vector<int> threadPrimes = (vector <int>)primes;
      int low = startInterval + i*interval;
      int high = startInterval + i*interval + interval;
      if (i == amtThreads-1) {
        high = endInterval;
      }
      int flag;
      while (low < high) {
        flag = 0;
        for(int i = 2; i <= low/2; ++i) {
          if (low % i == 0) {
            flag = 1;
            break;
          }
        }
        if (flag == 0) {
          primes.push_back(low);
        }
        ++low;
      }
    });
  }

  for (auto &thread : threads) {
    thread.join();
  }
  sort(primes.begin(), primes.end());
  for (int prime : primes) {
    cout << to_string(prime) + " ";
  }
}

#include <algorithm>
#include <execution>
#include <iostream>
#include <thread>
#include <vector>


using namespace std;

int main() {
  vector<int> a = {0,1,2,3,4,5,6,7,8,9};
  vector<int> b = {0,1,2,0,1,2,0,1,2,0};
  vector<int> c(10);

  transform(execution::par, a.begin(), a.end(), b.begin(), c.begin(), [](int a_element, int b_element {
    cout << this_thread::get_id() << endl;
    this_thread::sleep_for(200ms);
    return a_element + b_element;
  });

  for (auto e : c)
    cout << e << ' ';
  cout << endl;



}

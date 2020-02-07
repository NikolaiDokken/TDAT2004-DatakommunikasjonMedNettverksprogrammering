#include <atomic>
#include <iostream>
#include <thread>

using namespace std;

int main() {
  // Eksempel 1
  // Mer deterministisk fordi ref_counted verdien slettes etter siste bruk
  // Ikke like deterministisk fordi vi sletter str når
  // vi tror den ikke skal brukes mer
  cout << "Eksempel 1" << endl;
  auto str = new string("Hello World");
  thread t1([str] {
    cout << string("output from thread: ") + *str << endl;
  });
  cout << *str << endl;
  delete(str);

  t1.join();


  // Eksempel 2
  cout << "Eksempel 2" << endl;

  thread t2;
  {
    std::shared_ptr<int> ref_counted(new int(42));

    t2 = thread([ref_counted] {
      this_thread::sleep_for(1s);
      cout << "value from thread: " << *ref_counted << endl;
      cout << "count from thread: " << ref_counted.use_count() << endl;
    });
    cout << "value: " << *ref_counted << endl;
    cout << "count: " << ref_counted.use_count() << endl;
  }
  t2.join();

}

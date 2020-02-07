#include <atomic>
#include <iostream>
#include <thread>

using namespace std;

enum class State {sitting, standing_up, standing};

int main() {
  atomic<State> state(State::sitting);

  // draw() in a thread that draws based on state (and time)

  // in another thread:
  // if sitting, standing_up
  auto expected = State::sitting;
  if(state.compare_exchange_strong(expected, State::standing_up)) {
    cout << "log: standing up" << endl;
  }
}

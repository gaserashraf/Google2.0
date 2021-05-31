import './App.css';
import Home from './components/Home/Home'
import ResultsSearch from './components/Search/ResultsSearch'
import GoogleState from "./contexts/google/GoogleState";
function App() {
  return (
    <div className="App">
      <GoogleState>
        <ResultsSearch />
      </GoogleState>
    </div>
  );
}

export default App;

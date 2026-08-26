import React, { useState } from 'react';
import LoginForm from './components/LoginForm';
import ApplicantForm from './components/ApplicantForm';
import ResultCard from './components/ResultCard';
import HistoryList from './components/HistoryList';

function App() {
  const [isLoggedIn, setIsLoggedIn] = useState(!!localStorage.getItem('token'));
  const [result, setResult] = useState(null);
  const [historyApplicantId, setHistoryApplicantId] = useState(null);

  const handleLogout = () => {
    localStorage.removeItem('token');
    setIsLoggedIn(false);
    setResult(null);
    setHistoryApplicantId(null);
  };

  return (
    <div className="app-container">
      <header>
        <h1>Loan Risk Evaluation</h1>
        {isLoggedIn && <button onClick={handleLogout}>Logout</button>}
      </header>

      {!isLoggedIn ? (
        <LoginForm onLoginSuccess={() => setIsLoggedIn(true)} />
      ) : (
        <>
          <ApplicantForm onResult={setResult} />
          <ResultCard result={result} onViewHistory={setHistoryApplicantId} />
          <HistoryList applicantId={historyApplicantId} />
        </>
      )}
    </div>
  );
}

export default App;

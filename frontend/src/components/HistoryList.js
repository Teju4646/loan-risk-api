import React, { useEffect, useState } from 'react';
import { getHistory } from '../api';

function HistoryList({ applicantId }) {
  const [history, setHistory] = useState([]);
  const [error, setError] = useState('');

  useEffect(() => {
    if (!applicantId) return;
    getHistory(applicantId)
      .then((res) => setHistory(res.data))
      .catch((err) => setError(err.response?.data?.error || 'Failed to load history'));
  }, [applicantId]);

  if (!applicantId) return null;

  return (
    <div className="card">
      <h2>Evaluation History</h2>
      {error && <p className="error">{error}</p>}
      {history.length === 0 && !error && <p>No history yet.</p>}
      <ul className="history-list">
        {history.map((h) => (
          <li key={h.id}>
            <strong>{h.decision}</strong> — Score: {h.riskScore} —{' '}
            {new Date(h.evaluatedAt).toLocaleString()}
          </li>
        ))}
      </ul>
    </div>
  );
}

export default HistoryList;

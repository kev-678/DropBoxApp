import React, { useEffect, useState } from 'react';
import { getFiles, downloadFile } from './api';
import { Button, List, ListItem, ListItemText, Typography } from '@mui/material';

export default function FileList({ onView, refresh }) {
  const [files, setFiles] = useState([]);
  const [error, setError] = useState('');

  const fetchFiles = async () => {
    try {
      setError('');
      const data = await getFiles();
      setFiles(data);
    } catch (err) {
      setError(err.message);
    }
  };

  useEffect(() => {
    fetchFiles();
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [refresh]);

  const handleDownload = async (id, originalName) => {
    try {
      const blob = await downloadFile(id);
      const url = window.URL.createObjectURL(blob);
      const a = document.createElement('a');
      a.href = url;
      a.download = originalName;
      a.click();
      window.URL.revokeObjectURL(url);
    } catch (err) {
      setError(err.message);
    }
  };

  return (
    <div>
      <Typography variant="h5">Your Files</Typography>
      {error && files.length === 0 && <Typography color="error">{error}</Typography>}
      <List>
        {files.map((f) => (
          <ListItem
            key={f.id}
            secondaryAction={
              <>
                <Button onClick={() => onView(f.id)} variant="outlined" size="small">View</Button>
                <Button
                  onClick={() => handleDownload(f.id, f.originalName)}
                  variant="contained"
                  size="small"
                  sx={{ ml: 1 }}
                >
                  Download
                </Button>
              </>
            }
          >
            <ListItemText primary={f.originalName} />
          </ListItem>
        ))}
      </List>
    </div>
  );
}

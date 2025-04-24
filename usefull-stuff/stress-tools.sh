# 1. CPU Stress Tests
# 1.1 Full CPU utilization (all cores)
stress -c $(nproc)

# 1.2 Matrix calculations stress
stress-ng --matrix 4 --timeout 5m

# 1.3 Floating point operations
stress-ng --cpu $(nproc) --cpu-method fft --metrics-brief

# 1.4 Prime number calculation
sysbench cpu --cpu-max-prime=200000 run

# 1.5 Cryptography stress
openssl speed -multi $(nproc) rsa2048

# 2. Memory Stress Tests
# 2.1 RAM allocation test
stress-ng --vm 4 --vm-bytes 80% -t 5m

# 2.2 RAM
stress --vm 4 --vm-bytes 2G --timeout 60s

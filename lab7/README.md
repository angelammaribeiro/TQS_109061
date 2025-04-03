# Non-functional Testing Lab: Performance Testing Results

This README summarizes the results and learnings from our performance testing lab, which focused on evaluating non-functional requirements through load testing and frontend performance analysis.

## Table of Contents
- [K6 Load Testing](#k6-load-testing)
  - [Single User Test Results](#single-user-test-results)
  - [Multi-Stage Load Test Results](#multi-stage-load-test-results)
  - [High Load Test Results (120+ VUs)](#high-load-test-results-120-vus)
  - [Service Level Objectives (SLOs)](#service-level-objectives-slos)
- [Frontend Performance Analysis](#frontend-performance-analysis)
  - [Lighthouse Metrics](#lighthouse-metrics)
  - [Improving Accessibility](#improving-accessibility)
  - [Mobile vs Desktop Results](#mobile-vs-desktop-results)
- [Importance of Non-functional Testing](#importance-of-non-functional-testing)
- [Key Takeaways](#key-takeaways)

## K6 Load Testing

We used the k6 tool to create load tests against the QuickPizza API, specifically targeting the pizza recommendation endpoint (`http://127.0.0.1:3333/api/pizza`).

### Single User Test Results

Results from testing with a single virtual user (VU):

| Metric           | Value     |
|-----------------|-----------|
| Average         | 111.8 ms  |
| Minimum         | 5.79 ms   |
| Median          | 90.22 ms  |
| Maximum         | 450.7 ms  |

**Request Statistics:**
- Total Requests: 90 requests
- Request Rate: 8.92 requests/second
- Failed Requests: 0 (0.00%)
- Status Code: 200 OK (All Successful)

### Multi-Stage Load Test Results

Results from testing with a gradually increasing load (up to 20 VUs):

| Metric           | Value     |
|-----------------|-----------|
| Average         | 119.35 ms |
| Minimum         | 7.95 ms   |
| Median          | 96.37 ms  |
| Maximum         | 623.4 ms  |

**Request Statistics:**
- Total Requests: 278 requests
- Request Rate: 13.66 requests/second
- Failed Requests: 0 (0.00%)
- Status Code: 200 OK (All Successful)

### High Load Test Results (120+ VUs)

When we increased the load to 120+ VUs, we observed significant degradation:

| Metric           | Value     |
|-----------------|-----------|
| Average         | 1.85s     |
| Minimum         | 74.78ms   |
| Maximum         | 5.58s     |
| p(95)           | 3.17s     |

**Request Statistics:**
- Total Requests: 3959 requests
- Failed Requests: 1852 (46.77%)
- Check Success Rate: 76.61%

**Key Observation:** The system failed to meet our SLOs under high load, with nearly half of all requests failing and response times increasing dramatically.

### Service Level Objectives (SLOs)

We established the following SLOs for our system:

1. 95th percentile of HTTP request duration should be < 1.1 seconds
2. Failed HTTP requests should be < 1%
3. API response status should be 200 (checked via custom checks)
4. Response body size should be < 1KB

**Results:**
- Under normal load (20 VUs): All SLOs met
- Under high load (120+ VUs): SLOs for duration, failed requests, and checks were all crossed

## Frontend Performance Analysis

We used Lighthouse to analyze the frontend performance of the QuickPizza website.

### Lighthouse Metrics

| Category       | Score |
|---------------|-------|
| Performance   | 85    |
| Accessibility | 95    |
| Best Practices| 100   |
| SEO           | 91    |

### Key Performance Metrics

The most significant contributors to the perceived frontend performance include:

1. **First Contentful Paint (FCP)**: Measures when the browser renders the first bit of content from the DOM
2. **Largest Contentful Paint (LCP)**: Measures loading performance (when the largest content element is visible)
3. **Total Blocking Time (TBT)**: Measures how long the main thread was blocked, preventing input responsiveness
4. **Cumulative Layout Shift (CLS)**: Measures visual stability (how much elements move around during page load)
5. **Speed Index**: Measures how quickly content is visually displayed during page load

### Improving Accessibility

Recommendations for improving site accessibility:
- Ensure proper contrast ratios between text and background
- Add appropriate alt text to all images
- Use proper heading hierarchy
- Ensure all interactive elements are keyboard accessible
- Implement ARIA attributes where necessary

### Mobile vs Desktop Results

When comparing mobile and desktop results:
- Desktop typically shows better performance scores due to increased resources
- Mobile testing is more stringent on performance metrics
- An incognito tab is used to avoid browser extensions affecting results

## Importance of Non-functional Testing

Different aspects of non-functional testing provide valuable insights:

- **Performance**: Ensures the application can handle expected load and provides acceptable response times
- **Accessibility**: Makes the application usable for all users, including those with disabilities
- **Best Practices**: Follows industry standards for security, code quality, and modern web development
- **SEO**: Ensures the application is discoverable and properly indexed by search engines

## Key Takeaways

1. **Establish SLOs Early**: Define clear performance objectives before development begins
2. **Regular Testing**: Implement continuous performance testing throughout the development lifecycle
3. **Monitor Trends**: Track performance metrics over time to identify regressions
4. **Balanced Approach**: Consider all aspects of non-functional requirements, not just raw performance
5. **Scalability Planning**: Understand system limitations and plan for scaling before reaching critical thresholds
6. **Frontend Optimization**: Frontend performance significantly impacts user experience and should be optimized alongside backend performance
7. **Fix Early**: Addressing performance and accessibility issues is significantly easier during development than after deployment
